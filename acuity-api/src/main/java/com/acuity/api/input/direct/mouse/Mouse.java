package com.acuity.api.input.direct.mouse;

import com.acuity.api.AcuityInstance;
import com.acuity.api.input.direct.mouse.impl.BasicMouseDriver;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

import java.awt.event.MouseEvent;

/**
 * Created by Zachary Herridge on 6/26/2017.
 */
public class Mouse {

    private static MouseDriver mouseDriver = new BasicMouseDriver();

    public static int LEFT = MouseEvent.BUTTON1;
    public static int MIDDLE = MouseEvent.BUTTON2;
    public static int RIGHT = MouseEvent.BUTTON3;

    public static MouseDriver getDriver(){
        return mouseDriver;
    }

    public static MouseFuture click(ScreenLocation screenLocation){
        return click(screenLocation, true);
    }

    public static MouseFuture click(ScreenLocation screenLocation, boolean leftClick){
        return getDriver().queue(new MouseFuture()
                .setScreenLocation(screenLocation)
                .setMouseButton(leftClick ? LEFT : RIGHT));
    }

    public static int[] getHoveredUIDs(){
        return AcuityInstance.getClient().getHoveredUIDs();
    }

    public static int getHoveredCount(){
        return AcuityInstance.getClient().getHoveredCount();
    }

    public static ScreenLocation getPosition(){
        return AcuityInstance.getAppletManager()
                .getMouseMiddleMan()
                .getMousePosition()
                .map(ScreenLocation::fromPoint)
                .orElse(null);
    }

    public static MouseFuture move(ScreenLocation screenLocation) {
        return getDriver()
                .queue(new MouseFuture().setScreenLocation(screenLocation));
    }
}
