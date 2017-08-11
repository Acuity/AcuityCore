package com.acuity.control.server.messages.handlers.impl;

import com.acuity.control.server.messages.handlers.MessageHandler;
import com.acuity.control.server.sessions.Session;
import com.acuity.control.server.sessions.Sessions;
import com.acuity.control.server.websockets.WSocket;
import com.acuity.control.server.websockets.WSocketEvent;
import com.acuity.db.domain.common.message_data.LoginData;
import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.services.impl.AcuityAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * Created by Zachary Herridge on 8/3/2017.
 */
public class LoginHandler extends MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

    private static final MessagePackage BAD_LOGIN = new MessagePackage(MessagePackage.Type.BAD_LOGIN, null);

    public LoginHandler(WSocket wSocket) {
        super(wSocket);
    }

    @Override
    public void handle(MessagePackage messagePackage) {
        if (messagePackage.getMessageType() == MessagePackage.Type.LOGIN){
            LoginData loginData = messagePackage.getBodyAs(LoginData.class);
            if (loginData.getEmail() != null && loginData.getPassword() != null){
                logger.debug("Attempting login for '{}'.", loginData.getEmail());
                AcuityAccount acuityAccount = AcuityAccountService.getInstance()
                        .checkLoginByEmail(loginData.getEmail(), loginData.getPassword())
                        .orElse(null);

                if (acuityAccount != null){
                    logger.debug("Found account for '{}'.", loginData.getEmail());
                    if (loginData.getSessionType() == 1){
                        logger.debug("Session type found for {}.", loginData.getSessionType());
                        getSocket().getEventBus().register(new BotClientHandler(getSocket()));
                    }
                    else {
                        logger.debug("Failed to find session type found for {}.", loginData.getSessionType());
                        getSocket().send(BAD_LOGIN);
                        return;
                    }

                    Session session = getSocket().getSession();
                    if (session == null){
                        session = Sessions.createSession();
                        getSocket().setSession(session);
                    }
                    session.setAttribute(AcuityAccount.class, acuityAccount);
                    destroy();
                    getSocket().send(new MessagePackage(MessagePackage.Type.GOOD_LOGIN, null).setBody(Collections.singletonMap("setAcuityAccount", acuityAccount)));
                    getSocket().getEventBus().post(new WSocketEvent.LoginComplete(messagePackage));
                }
                else {
                    logger.debug("Bad login for '{}'.", loginData.getSessionType());
                    getSocket().send(BAD_LOGIN);
                }
            }
        }
    }
}
