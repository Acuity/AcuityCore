package com.acuity.control.server.websockets;

import com.acuity.control.server.messages.handlers.impl.LoginHandler;
import com.acuity.control.server.sessions.Session;
import com.acuity.control.server.sessions.Sessions;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.util.Json;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.java_websocket.WebSocket;

/**
 * Created by Zachary Herridge on 8/4/2017.
 */
public class WSocket implements SubscriberExceptionHandler{

    private WebSocket socket;
    private Session session;
    private EventBus eventBus = new EventBus(this);

    public WSocket(WebSocket socket) {
        this.socket = socket;
        eventBus.register(new LoginHandler(this));
    }

    public void send(MessagePackage messagePackage){
        socket.send(Json.GSON.toJson(messagePackage));
    }

    public void onMessage(String message){
        System.out.println(message);
        MessagePackage messagePackage = Json.GSON.fromJson(message, MessagePackage.class);
        if (messagePackage != null) eventBus.post(messagePackage);
    }

    public void onError(Exception e) {
        e.printStackTrace();
        eventBus.post(new WSocketEvent.Error(e, null));
    }

    public void onClose(int code, String reason, boolean remote) {
        eventBus.post(new WSocketEvent.Closed(code, reason, remote));
        if (session != null) Sessions.closeSession(session);
    }

    public void onOpen() {
        eventBus.post(new WSocketEvent.Opened());
    }

    public WebSocket getSocket() {
        return socket;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Session getSession(){
        return session;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public void handleException(Throwable throwable, SubscriberExceptionContext subscriberExceptionContext) {
        throwable.printStackTrace();
        eventBus.post(new WSocketEvent.Error(throwable, subscriberExceptionContext));
    }
}
