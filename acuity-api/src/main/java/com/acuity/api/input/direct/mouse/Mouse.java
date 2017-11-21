package com.acuity.api.input.direct.mouse;

import com.acuity.api.AcuityInstance;
import com.acuity.api.input.direct.mouse.impl.BasicMouseDriver;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

/**
 * Created by Zachary Herridge on 6/26/2017.
 */
public class Mouse {

    private static MouseDriver mouseDriver = new BasicMouseDriver();

    public static MouseDriver getDriver(){
        return mouseDriver;
    }

    public static void click(ScreenLocation screenLocation){
        click(screenLocation, true);
    }

    public static void click(ScreenLocation screenLocation, boolean leftClick){
        getDriver().click(screenLocation, leftClick);
    }

    public static int[] getHoveredUIDs(){
        return AcuityInstance.getClient().getHoveredUIDs();
    }

    public static int getHoveredCount(){
        return AcuityInstance.getClient().getHoveredCount();
    }

    public static ScreenLocation getPosition(){
        return AcuityInstance.getAppletManager().getMouseMiddleMan().getMousePosition()
                .map(ScreenLocation::fromPoint)
                .orElse(null);
    }

    public static void move(ScreenLocation screenLocation) {
        getDriver().move(screenLocation);
    }
}
