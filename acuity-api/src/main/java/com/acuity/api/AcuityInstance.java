package com.acuity.api;

import com.acuity.api.applet.RSAppletLoader;
import com.acuity.api.applet.RSAppletStub;
import com.acuity.api.rs.wrappers.engine.Client;
import com.acuity.api.script.ScriptManager;
import com.acuity.rs.api.RSClient;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.applet.Applet;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class AcuityInstance {

    private static final Logger logger = LoggerFactory.getLogger(AcuityInstance.class);

    private static Client client;

    private static Applet applet;
    private static RSAppletLoader appletLoader;
    private static RSAppletStub rsAppletStub;
    private static ScriptManager scriptManager;

    private static EventBus rsEventBus = new EventBus();// TODO: 6/14/2017 Inject this?

    public static void init() throws Exception {
        logger.info("Applet loading started.");
        appletLoader = new RSAppletLoader();
        applet = appletLoader.loadApplet();
        scriptManager = new ScriptManager();
    }

    public static void loadClient(){
        logger.info("RSClient loading started.");
        rsAppletStub = new RSAppletStub(appletLoader.getRsConfig(), applet);
        applet.setStub(rsAppletStub);

        /*
          Temporary fix for mac / linux is fails to load
          due to size of 0 applet.
         */
        applet.setSize(800, 600);

        logger.info("Booting applet.");
        applet.init();
        applet.start();
        client = ((RSClient) applet).getWrapper();
        logger.debug("RSClient loading finished.");
    }

    public static Applet getApplet() {
        return applet;
    }

    public static RSAppletLoader getAppletLoader() {
        return appletLoader;
    }

    public static RSAppletStub getRsAppletStub() {
        return rsAppletStub;
    }

    public static ScriptManager getScriptManager() {
        return scriptManager;
    }

    @NotNull
    public static Client getClient(){
        return Preconditions.checkNotNull(client, "Make sure the client is loaded before referencing it.");
    }

    public static EventBus getEventBus() {
        return rsEventBus;
    }
}
