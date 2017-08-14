package com.acuity.control.client;

import com.acuity.control.client.websockets.WClient;
import com.acuity.control.client.websockets.WClientEvent;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.util.Json;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.java_websocket.drafts.Draft_6455;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Zach on 8/5/2017.
 */
public class AcuityWSClient {

    private static final Logger logger = LoggerFactory.getLogger(AcuityWSClient.class);
    private static final AcuityWSClient INSTANCE = new AcuityWSClient();

    public static AcuityWSClient getInstance() {
        return INSTANCE;
    }

    private WClient wClient;

    private EventBus eventBus = new EventBus();
    private Executor executor = Executors.newSingleThreadExecutor();
    private boolean reconnect = true;
    private long reconnectDelay = 3000;
    private String lastHost;

    public void start(String host) throws URISyntaxException {
        this.lastHost = host;
        this.reconnect = true;
        wClient = new WClient(this.lastHost, new Draft_6455());
        wClient.setConnectionLostTimeout(5);
        wClient.getEventBus().register(this);
        wClient.connect();
    }

    public void setReconnect(boolean reconnect){
        this.reconnect = reconnect;
    }

    public void stop(){
        reconnect = false;
        if (wClient != null) wClient.close();
    }

    public void setReconnectDelay(long reconnectDelay) {
        this.reconnectDelay = reconnectDelay;
    }

    public long getReconnectDelay() {
        return reconnectDelay;
    }

    public boolean isReconnect() {
        return reconnect;
    }

    public void send(MessagePackage messagePackage){
        System.out.println(messagePackage);
        wClient.send(Json.GSON.toJson(messagePackage));
    }

    public EventBus getEventBus(){
        return eventBus;
    }

    public boolean isConnected(){
        return wClient.isOpen();
    }

    @Subscribe
    public void onMessage(MessagePackage messagePackage){
        logger.debug("onMessage: {}.", messagePackage);
        if (messagePackage != null) eventBus.post(messagePackage);
    }

    @Subscribe
    public void onOpen(WClientEvent.Opened opened){
        logger.info("Web socket opened.");
        eventBus.post(opened);
    }

    @Subscribe
    public void onClose(WClientEvent.Closed closed){
        logger.info("Web socket closed.");
        if (reconnect){
            executor.execute(new Recconect());
        }
        eventBus.post(closed);
    }

    private class Recconect implements Runnable{

        @Override
        public void run() {
            try {
                logger.debug("Sleeping for reconnect delay of {}ms.", reconnectDelay);
                Thread.sleep(reconnectDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                logger.info("Attempting reconnect.");
                start(lastHost);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }
}
