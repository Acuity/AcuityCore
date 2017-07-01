package com.acuity.inject.callbacks;

import com.acuity.api.AcuityInstance;
import com.acuity.api.Events;
import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.events.impl.GameStateChangeEvent;
import com.acuity.api.rs.events.impl.OverheadPrayerChangeEvent;
import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.query.SceneElements;
import com.acuity.api.rs.utils.Game;
import com.acuity.api.rs.utils.LocalPlayer;
import com.acuity.api.rs.utils.Scene;
import com.acuity.api.rs.wrappers.peers.scene.SceneTile;
import com.acuity.rs.api.RSPlayer;
import com.acuity.rs.api.RSRenderable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Comparator;
import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/7/2017.
 */
public class Callbacks {

    private static final Logger logger = LoggerFactory.getLogger(Callbacks.class);

    @ClientInvoked
    public static void postFieldChangeCallback(String name, int index, Object object) {
        logger.debug("'{}' called with index={} and object={}", name, index, object);
        switch (name) {
            case "playerPrayerChange":
                Events.getRsEventBus().post(new OverheadPrayerChangeEvent(((RSPlayer) object).getWrapper()));
                break;
            case "gameStateChanged":
                Events.getRsEventBus().post(new GameStateChangeEvent(AcuityInstance.getClient().getGameState()));
                break;
        }
    }

    @ClientInvoked
    public static void cachedModelUpdated(RSRenderable rsRenderable){
    }

    @ClientInvoked
    public static void tick() {

    }

    @ClientInvoked
    public static void drawCallback(Image image) {
/*        try {
            if (Game.getGameState() == Game.IN_GAME) {
                SceneElements.streamLoaded().sorted(Comparator.comparingInt(Locatable::distance)).forEach(sceneElement -> {
                    sceneElement.getModel().ifPresent(model -> {
                        model.streamPolygons().forEach(polygon -> {
                            image.getGraphics().drawPolygon(polygon);
                        });

                    });
                });
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
