package com.acuity.api.rs.wrappers.peers.engine;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.rs.api.RSGameEngine;
import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.applet.Applet;
import java.awt.*;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class GameEngine {

    private static final Logger logger = LoggerFactory.getLogger(GameEngine.class);

    private RSGameEngine rsGameEngine;

    @ClientInvoked
    public GameEngine(RSGameEngine peer) {
        this.rsGameEngine = Preconditions.checkNotNull(peer);
    }

    public Canvas getCanvas(){
        return rsGameEngine.getCanvas();
    }

    public Applet getApplet(){
        return (Applet) getRsGameEngine();
    }


    public RSGameEngine getRsGameEngine() {
        return rsGameEngine;
    }
}
