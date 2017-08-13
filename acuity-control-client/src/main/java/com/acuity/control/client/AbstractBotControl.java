package com.acuity.control.client;

import com.acuity.control.client.websockets.WClientEvent;
import com.acuity.db.domain.common.message_data.LoginData;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.domain.vertex.impl.RSAccount;
import com.acuity.db.domain.vertex.impl.bot_clients.BotClientConfig;
import com.acuity.db.domain.vertex.impl.scripts.Script;
import com.acuity.security.DBAccess;
import com.google.common.eventbus.Subscribe;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Zachary Herridge on 8/9/2017.
 */
public abstract class AbstractBotControl {

    public void start() throws Exception {
        AcuityWSClient.getInstance().getEventBus().register(this);
        AcuityWSClient.getInstance().start("ws://acuitybotting.com:8015");
    }

    public void stop(){
        AcuityWSClient.getInstance().getEventBus().unregister(this);
        AcuityWSClient.getInstance().stop();
    }

    @Subscribe
    public void onConnect(WClientEvent.Opened opened){
        AcuityWSClient.getInstance().send(new MessagePackage(MessagePackage.Type.LOGIN, null).setBody(
                new LoginData("zgherridge@gmail.com", "VaadinLife!0!", 1)
        ));
    }

    public void send(MessagePackage messagePackage){
        AcuityWSClient.getInstance().send(messagePackage);
    }

    @Subscribe
    public void onMessage(MessagePackage messagePackage){
        if (messagePackage.getMessageType() == MessagePackage.Type.GOOD_LOGIN){
            sendMachineInfo();
        }

        if (messagePackage.getMessageType() == MessagePackage.Type.CONFIG_UPDATE){
            BotClientConfig config = messagePackage.getBodyAs(BotClientConfig.class);
            updateConfig(config);
        }

        if (messagePackage.getMessageType() == MessagePackage.Type.ACCOUNT_ASSIGNMENT_CHANGE){
            RSAccount account = messagePackage.getBodyAs(RSAccount.class);
            updateAccount(account);
        }

        handleMessage(messagePackage);
    }

    public abstract void updateAccount(RSAccount rsAccount);

    public abstract void updateConfig(BotClientConfig config);

    public abstract void handleMessage(MessagePackage messagePackage);


    private void sendMachineInfo(){
     /*   MessagePackage machineInfo = new MessagePackage(MessagePackage.Type.MACHINE_INFO);
        for (String prop : System.getProperties().stringPropertyNames()) {
            machineInfo.putBody(prop, System.getProperty(prop));
        }
        send(machineInfo);*/
    }

    public static void main(String[] args) {
        try {
            AbstractBotControl abstractBotControl = new AbstractBotControl() {

                @Override
                public void updateAccount(RSAccount rsAccount) {

                }

                @Override
                public void updateConfig(BotClientConfig config) {

                }

                @Override
                public void handleMessage(MessagePackage messagePackage) {
                    Object result = messagePackage.getBodyAsType();
                    if (result != null){
                        if ("kill-bot".equals(result)){
                            System.exit(0);
                        }
                    }
                }
            };
            abstractBotControl.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
