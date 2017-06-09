package com.acuity.api.rs.peers;

import com.acuity.rs.api.RSGameEngine;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class GameEngine {

    private static final Logger logger = LoggerFactory.getLogger(GameEngine.class);

    private RSGameEngine rsGameEngine;

    public GameEngine(@NotNull RSGameEngine peer) {
        Preconditions.checkNotNull(peer);
        this.rsGameEngine = peer;
    }

    public Canvas getCanvas(){
        return rsGameEngine.getCanvas();
    }

    public RSGameEngine getRsGameEngine() {
        logger.trace("Accessing peer directly via getter.");
        return rsGameEngine;
    }
}