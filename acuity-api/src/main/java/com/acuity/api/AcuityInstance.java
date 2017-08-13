package com.acuity.api;

import com.acuity.api.applet.AppletManager;
import com.acuity.api.meta.AcuitySettings;
import com.acuity.api.rs.utils.task.login.Account;
import com.acuity.api.rs.wrappers.peers.engine.Client;
import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class AcuityInstance {

    private static final Logger logger = LoggerFactory.getLogger(AcuityInstance.class);

    private static AcuityInstance instance;

    private AcuitySettings acuitySettings;
    private AppletManager appletManager;

    private static Account rsAccount = new Account(null, null); // TODO: 7/8/2017 change

    public static Account getRsAccount() {
        return rsAccount;
    }

    public static void setRsAccount(Account rsAccount) {
        AcuityInstance.rsAccount = rsAccount;
    }

    private AcuityInstance() throws Exception {
        logger.info("Applet loading started.");
        acuitySettings = new AcuitySettings();
        appletManager = new AppletManager();
    }

    public static void init() throws Exception {
        instance = new AcuityInstance();
        getAppletManager().load();
    }

    public static void boot(){
        getAppletManager().boot();
    }

    public static AppletManager getAppletManager() {
        return Preconditions.checkNotNull(instance, "Init the AcuityInstance before referencing it.").appletManager;
    }

    public static AcuitySettings getSettings() {
        return Preconditions.checkNotNull(instance, "Init the AcuityInstance before referencing it.").acuitySettings;
    }

    public static Client getClient(){
        return getAppletManager().getClient();
    }
}
