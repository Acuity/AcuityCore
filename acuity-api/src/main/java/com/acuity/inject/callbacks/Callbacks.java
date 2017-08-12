package com.acuity.inject.callbacks;

import com.acuity.api.AcuityInstance;
import com.acuity.api.Events;
import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.events.impl.*;
import com.acuity.api.rs.events.impl.drawing.GameDrawEvent;
import com.acuity.api.rs.events.impl.drawing.InGameDrawEvent;
import com.acuity.api.rs.utils.Game;
import com.acuity.rs.api.RSActor;
import com.acuity.rs.api.RSAxisAlignedBoundingBox;
import com.acuity.rs.api.RSPlayer;
import com.acuity.rs.api.RSRenderable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Created by Zachary Herridge on 6/7/2017.
 */
public class Callbacks {

    private static final Logger logger = LoggerFactory.getLogger(Callbacks.class);

    @ClientInvoked
    public static void fieldUpdating(String name, int index, Object instance) {
        logger.debug("Field Updating: '{}' with index={} and instance={}", name, index, instance);
        switch (name) {

        }
    }

    @ClientInvoked
    public static void fieldUpdated(String name, int index, Object instance) {
        try {
            if (!name.equals("mouseXHistory") && !name.equals("mouseYHistory")) {
                //logger.debug("Field Updated: '{}' with index={} and instance={}", name, index, instance);
            }
            switch (name) {
                //836 death anim
                case "actorAnimationChange":
                    if (instance == null || (!(instance instanceof RSPlayer))) {
                        break;
                    }

                    final RSActor actor = (RSActor) instance;
                    Events.getRsEventBus().post(new ActorAnimationChangeEvent(actor, actor.getAnimation()));
                    break;
                case "gameState":
                    Events.getRsEventBus().post(new GameStateChangeEvent(AcuityInstance.getClient().getGameState()));
                    break;
                case "mouseYHistory":
                    try {
                        int lastX = AcuityInstance.getClient().getRsClient().getMouseRecorder().getMouseXHistory()[index];
                        int lastY = AcuityInstance.getClient().getRsClient().getMouseRecorder().getMouseYHistory()[index];
                        Events.getRsEventBus().post(new MouseRecorderUpdateEvent(System.currentTimeMillis(), lastX, lastY));
                    } catch (Exception e) {

                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ClientInvoked
    public static void insertMenuItemCallback(String action, String target, int opcode, int arg0, int arg1, int arg2) {
        try {
            Events.getRsEventBus().post(new MenuInsertEvent(opcode, arg0, arg1, arg2, action, target));
        } catch (Throwable e) {
            logger.error("Error during process menu insert callback.", e);
        }
    }

    @ClientInvoked
    public static void processActionCallback(int arg1, int arg2, int opcode, int arg0, String action, String target, int clickX, int clickY) {
        try {
            Events.getRsEventBus().post(new ActionEvent(opcode, arg0, arg1, arg2, action, target, clickX, clickY));
        } catch (Throwable e) {
            logger.error("Error during process action callback.", e);
        }
    }

    @ClientInvoked
    public static void tick() {
        try {
            GameTickEvent.incrementTick();
            Events.getRsEventBus().post(GameTickEvent.INSTANCE);
        } catch (Throwable e) {
            logger.error("Error during tick callback.", e);
        }
    }

    @ClientInvoked
    public static void addHitUpdateCallback(RSActor actor, int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            if (actor != null && actor instanceof RSPlayer) {
                final RSPlayer player = (RSPlayer) actor;
                Events.getRsEventBus().post(new PlayerHealthChangeEvent(player.getWrapper(), player.getWrapper().getHealthPercent()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ClientInvoked
    public static void boundingBoxUpdated(RSRenderable rsRenderable, RSAxisAlignedBoundingBox rsAxisAlignedBoundingBox) {

    }

    @ClientInvoked
    public static void drawCallback(Image image) {
        try {
            if (Game.State.IN_GAME.isCurrent()) {
                Events.getRsEventBus().post(new InGameDrawEvent(image));
            } else {
                Events.getRsEventBus().post(new GameDrawEvent(image));
            }
        } catch (Throwable e) {
            logger.error("Error during draw callback.", e);
        }
    }
}
