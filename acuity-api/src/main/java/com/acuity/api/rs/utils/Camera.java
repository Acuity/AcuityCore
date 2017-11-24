package com.acuity.api.rs.utils;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.wrappers.common.locations.WorldLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class Camera {

    public static int getX(){
        return AcuityInstance.getClient().getCameraX();
    }

    public static int getY(){
        return AcuityInstance.getClient().getCameraY();
    }

    public static int getZ(){
        return AcuityInstance.getClient().getCameraZ();
    }

    public static int getPitch(){
        return AcuityInstance.getClient().getCameraPitch();
    }

    public static int getYaw(){
        return AcuityInstance.getClient().getCameraYaw();
    }

    public static boolean isVisible(ScreenLocation screenLocation){
        return Projection.GAME_SCREEN.contains(screenLocation);
    }

    public static int getAngle() {
        return (int) ((360D / 2048) * Math.min(2047 - getYaw(), 2048));
    }

    public static int getAngleTo(Locatable target) {
        WorldLocation local = LocalPlayer.getWorldLocation().orElse(null);
        WorldLocation other = Optional.ofNullable(target).map(Locatable::getWorldLocation).orElse(null);

        if (local == null) throw new NullPointerException("Failed to load LocalPlayer.");
        if (other == null) throw new NullPointerException("Failed to load target location.");

        int angle = 90 - ((int) Math.toDegrees(Math.atan2(other.getWorldY() - local.getWorldY(), other.getWorldX() - local.getWorldX())));
        if (angle < 0) angle += 360;
        return angle % 360;
    }
}
