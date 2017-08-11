package com.acuity.botcontrol;

import com.acuity.control.client.AcuityWSClient;
import com.acuity.control.client.websockets.WClientEvent;
import com.acuity.db.domain.common.message_data.LoginData;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.domain.vertex.impl.RSAccount;
import com.acuity.db.domain.vertex.impl.bot_clients.BotClientConfig;
import com.acuity.security.DBAccess;
import com.google.common.eventbus.Subscribe;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

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

        if (messagePackage.getMessageType() == MessagePackage.Type.CONFIG_UPDATE){
            BotClientConfig config = messagePackage.getBodyAs(BotClientConfig.class);
            System.out.println(config);
            if (config != null && config.getScript() != null){
                String jarURL = config.getScript().getJarURL();
                System.out.println("Got Script(" + config.getScript().getScriptRev() + "): " + jarURL);

                if (jarURL != null){
                    try {
                        URLClassLoader child = new URLClassLoader (new URL[] {new URL(jarURL)}, this.getClass().getClassLoader());
                        Class<?> client = Class.forName("client", true, child);
                        System.out.println(child);
                        System.out.println();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (messagePackage.getMessageType() == MessagePackage.Type.ACCOUNT_ASSIGNMENT_CHANGE){
            RSAccount account = messagePackage.getBodyAs(RSAccount.class);
            System.out.println(account);
        }

        Object result = messagePackage.getBodyAsType();
        if (result != null){
            if ("kill-bot".equals(result)){
                System.exit(0);
            }
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
