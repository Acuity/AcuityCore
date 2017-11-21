package com.acuity.api.applet;

import com.acuity.api.Events;
import com.acuity.api.applet.input.impl.FocusMiddleMan;
import com.acuity.api.applet.input.impl.KeyboardMiddleMan;
import com.acuity.api.applet.input.impl.MouseMiddleMan;
import com.acuity.api.applet.loader.ClientConfig;
import com.acuity.api.applet.loader.ClientEnvironment;
import com.acuity.api.applet.loader.ClientStub;
import com.acuity.api.applet.loader.RSClassLoader;
import com.acuity.api.rs.events.impl.GameStateChangeEvent;
import com.acuity.api.rs.utils.Game;
import com.acuity.api.rs.wrappers.peers.engine.Client;
import com.acuity.rs.api.RSClient;
import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by Zach on 6/17/2017.
 */
public class AppletManager {

    private static final Logger logger = LoggerFactory.getLogger(AppletManager.class);

    private RSClassLoader rsClassLoader;

    private ClientConfig clientConfig;
    private ClientEnvironment<Client> clientEnvironment;
    private ClientStub clientStub;

    private FocusMiddleMan focusMiddleMan = new FocusMiddleMan();
    private MouseMiddleMan mouseMiddleMan = new MouseMiddleMan();
    private KeyboardMiddleMan keyboardMiddleMan = new KeyboardMiddleMan();
    private int initialWorld = 8;

    public AppletManager() throws Exception {
        Events.getRsEventBus().register(this);
    }

    @SuppressWarnings("unchecked")
    public void load(File gamepack) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.info("RSClient loading started.");
        clientConfig = new ClientConfig(initialWorld);
        rsClassLoader = new RSClassLoader(gamepack);
        Class<?> client = rsClassLoader.loadClass("client");
        clientEnvironment = new ClientEnvironment(((RSClient) client.newInstance()).getWrapper());
        clientEnvironment.getGameEngine().getRsClient().setRedrawMode(2);
        clientStub = new ClientStub(clientConfig);
    }

    public void boot() {
        clientEnvironment.boot(clientStub);
    }

    @Subscribe
    public void gameStateChanged(GameStateChangeEvent changeEvent) {
        if (changeEvent.getPreviousGameState() == Game.State.CLIENT_LOADING.getIndex() && changeEvent.getGamestate() == Game.State.LOGIN_SCREEN.getIndex()) {
            focusMiddleMan.insertInto(getClient().getCanvas());
            mouseMiddleMan.insertInto(getClient().getCanvas());
            keyboardMiddleMan.insertInto(getClient().getCanvas());
            Events.getRsEventBus().unregister(this);
        }
    }

    public void setInitialWorld(int initialWorld) {
        if (initialWorld > 300) initialWorld -= 300;
        this.initialWorld = initialWorld;
    }

    public RSClassLoader getRsClassLoader() {
        return rsClassLoader;
    }

    public Client getClient() {
        return Preconditions.checkNotNull(clientEnvironment, "Load the RS client before referencing it.").getGameEngine();
    }

    public MouseMiddleMan getMouseMiddleMan() {
        return mouseMiddleMan;
    }

    public KeyboardMiddleMan getKeyboardMiddleMan() {
        return keyboardMiddleMan;
    }
}
