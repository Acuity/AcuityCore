package com.acuity.api.rs.utils;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

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
        return Projection.GAMESCREEN.contains(screenLocation);
    }
}
