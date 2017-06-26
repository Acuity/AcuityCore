package com.acuity.api.rs.utils;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.movement.SceneLocation;
import com.acuity.api.rs.movement.WorldLocation;
import com.acuity.api.rs.wrappers.scene.actors.Actor;
import com.acuity.api.rs.wrappers.scene.actors.impl.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class LocalPlayer {

    private static final Logger logger = LoggerFactory.getLogger(LocalPlayer.class);

    public static Optional<Player> get() {
        return AcuityInstance.getClient().getLocalPlayer();
    }

    public static Optional<WorldLocation> getWorldLocation() {
        return get().map(Player::getWorldLocation);
    }

    public static Optional<SceneLocation> getSceneLocation() {
        return get().map(Player::getSceneLocation);
    }

    public static boolean isAnimating() {
        return get().map(Actor::isAnimating).orElse(false);
    }

    public static Optional<String> getName() {
        return get().map(Player::getName);
    }
}
