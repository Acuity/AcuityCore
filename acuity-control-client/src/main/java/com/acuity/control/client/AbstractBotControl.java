package com.acuity.control.client;

import com.acuity.control.client.websockets.WClientEvent;
import com.acuity.db.domain.common.message_data.LoginData;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.domain.vertex.impl.RSAccount;
import com.acuity.db.domain.vertex.impl.bot_clients.BotClientConfig;
import com.google.common.eventbus.Subscribe;

/**
 * Created by Zachary Herridge on 8/9/2017.
 */
public abstract class AbstractBotControl {

    private String email;
    private String password;

    public void start(String email, String password) throws Exception {
        this.email = email;
        this.password = password;
        AcuityWSClient.getInstance().getEventBus().register(this);
        AcuityWSClient.getInstance().start("ws://acuitybotting.com:8015");
    }

    public void stop(){
        try {
            AcuityWSClient.getInstance().getEventBus().unregister(this);
        }catch (IllegalArgumentException ignored){
        }
        AcuityWSClient.getInstance().stop();
    }

    @Subscribe
    public void onConnect(WClientEvent.Opened opened){
        AcuityWSClient.getInstance().send(new MessagePackage(MessagePackage.Type.LOGIN, null).setBody(
                new LoginData(email, password, 1)
        ));
    }

    public void send(MessagePackage messagePackage){
        AcuityWSClient.getInstance().send(messagePackage);
    }

    public abstract void onGoodLogin();

    @Subscribe
    public void onMessage(MessagePackage messagePackage){
        if (messagePackage.getMessageType() == MessagePackage.Type.GOOD_LOGIN){
            onGoodLogin();
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
}
