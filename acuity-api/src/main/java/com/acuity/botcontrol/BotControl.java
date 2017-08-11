package com.acuity.botcontrol;

import com.acuity.control.client.AcuityWSClient;
import com.acuity.control.client.websockets.WClientEvent;
import com.acuity.db.domain.common.message_data.LoginData;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.util.Json;
import com.acuity.security.DBAccess;
import com.google.common.eventbus.Subscribe;

/**
 * Created by Zachary Herridge on 8/9/2017.
 */
public class BotControl {

    private static BotControl INSTANCE = new BotControl();

    public static BotControl getInstance() {
        return INSTANCE;
    }

    public BotControl() {
        AcuityWSClient.getInstance().getEventBus().register(this);
    }

    public void start() throws Exception {
        AcuityWSClient.getInstance().start("ws://localhost:8015");
    }

    public void stop(){
        AcuityWSClient.getInstance().stop();
    }

    @Subscribe
    public void onConnect(WClientEvent.Opened opened){
        AcuityWSClient.getInstance().send(new MessagePackage(MessagePackage.Type.LOGIN, null).setBody(
                new LoginData("zgherridge@gmail.com", DBAccess.getPassword2(), 1)
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

        try {
            Object o = Json.GSON.fromJson(messagePackage.getBodyJSON(), getClass().getClassLoader().loadClass(messagePackage.getBodyType()));

            if ("kill-bot".equals(o)){
                System.exit(0);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendMachineInfo(){
     /*   MessagePackage machineInfo = new MessagePackage(MessagePackage.Type.MACHINE_INFO);
        for (String prop : System.getProperties().stringPropertyNames()) {
            machineInfo.putBody(prop, System.getProperty(prop));
        }
        send(machineInfo);*/
    }

    public static void main(String[] args) {
        try {
            getInstance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
