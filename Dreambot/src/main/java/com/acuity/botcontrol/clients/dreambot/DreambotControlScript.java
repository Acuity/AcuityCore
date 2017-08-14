package com.acuity.botcontrol.clients.dreambot;

import com.acuity.control.client.AbstractBotControl;
import com.acuity.control.client.scripts.ScriptInstance;
import com.acuity.control.client.scripts.Scripts;
import com.acuity.db.domain.common.LoginFrame;
import com.acuity.db.domain.common.tracking.RSAccountState;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.domain.vertex.impl.Proxy;
import com.acuity.db.domain.vertex.impl.RSAccount;
import com.acuity.db.domain.vertex.impl.bot_clients.BotClientConfig;
import com.acuity.db.domain.vertex.impl.scripts.Script;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.PaintListener;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zach on 8/12/2017.
 */
@ScriptManifest(name = "AcuityBotControl", author = "AcuityBotting", category = Category.MISC, description = "", version = 0)
public class DreambotControlScript extends AbstractScript implements PaintListener{

    private Script script;
    private RSAccount account;
    private Proxy proxy;

    private AbstractScript dreambotScript;

    private LoginFrame loginFrame = new LoginFrame();

    private AbstractBotControl abstractBotControl = new AbstractBotControl() {
        @Override
        public void onGoodLogin() {
            loginFrame.dispose();
        }

        @Override
        public void updateAccount(RSAccount rsAccount) {
            account = rsAccount;
        }

        @Override
        public void updateConfig(BotClientConfig config) {
            proxy = config.getProxy();
            script = config.getScript();

            if (script != null){
                try {
                    ScriptInstance scriptInstance = Scripts.loadScript(script);
                    scriptInstance.loadJar();
                    Class result = scriptInstance.getScriptLoader().getLoadedClasses().values().stream().filter(aClass -> {
                        Class superclass = aClass.getSuperclass();
                        if (superclass != null && superclass.equals(AbstractScript.class)){
                            return true;
                        }
                        return false;
                    }).findAny().orElse(null);

                    if (result != null){
                        try {
                            AbstractScript abstractScript = (AbstractScript) result.newInstance();
                            abstractScript.registerMethodContext(getClient());
                            abstractScript.registerContext(getClient());
                            abstractScript.onStart();
                            dreambotScript = abstractScript;
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                AbstractScript abstractScript = DreambotControlScript.this.dreambotScript;
                dreambotScript = null;
                if (abstractScript != null) abstractScript.onExit();
            }
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


    private long lastSend = 0;
    private void sendAccountUpdate(){
        long timeMillis = System.currentTimeMillis();
        if (timeMillis - lastSend > TimeUnit.SECONDS.toMillis(5)){
            RSAccountState rsAccountState = new RSAccountState();

            if (getClient().isLoggedIn() && account != null){
                for (Skill skill : Skill.values()) {
                    rsAccountState.getSkillExperience().put(skill.getName(), getSkills().getExperience(skill));
                }
                rsAccountState.setHpPercent(getCombat().getHealthPercent());
                rsAccountState.setPrayerPoints(getSkills().getBoostedLevels(Skill.PRAYER));
                rsAccountState.setRunEnergy(getWalking().getRunEnergy());

                abstractBotControl.send(new MessagePackage(MessagePackage.Type.ACCOUNT_STATE_UPDATE, MessagePackage.SERVER).setBody(rsAccountState));
            }

            lastSend = timeMillis;
        }
    }

    public RSAccount getAccount() {
        return account;
    }

    @Override
    public void onStart() {
        loginFrame.getLoginButton().addActionListener(e -> {
            try {
                abstractBotControl.stop();
                abstractBotControl.start(loginFrame.getEmailField().getText(), loginFrame.getPasswordField().getText());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        loginFrame.setVisible(true);
    }

    @Override
    public void onPaint(Graphics graphics) {
        graphics.drawString("Script: " + script, 100, 100);
        graphics.drawString("Account: " + account, 100, 115);
        graphics.drawString("Proxy: " + proxy, 100, 130);
    }

    @Override
    public int onLoop() {
        sendAccountUpdate();
        return dreambotScript != null ? dreambotScript.onLoop() : 750;
    }

    @Override
    public void onExit() {
        abstractBotControl.stop();
    }

    public static void main(String[] args) {
        new DreambotControlScript().onStart();
    }
}
