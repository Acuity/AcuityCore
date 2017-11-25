package com.acuity.api.input.direct.mouse;

import com.acuity.api.AcuityInstance;
import com.acuity.api.input.direct.mouse.impl.BasicMouseDriver;
import com.acuity.api.input.direct.mouse.pathing.MousePathGenerator;
import com.acuity.api.input.direct.mouse.pathing.impl.BasicMousePathGenerator;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenPolygon;

import java.awt.event.MouseEvent;

/**
 * Created by Zachary Herridge on 6/26/2017.
 */
public class Mouse {

    private static MouseDriver mouseDriver = new BasicMouseDriver();
    private static MousePathGenerator mousePathGenerator = new BasicMousePathGenerator();

    public static int LEFT = MouseEvent.BUTTON1;
    public static int MIDDLE = MouseEvent.BUTTON2;
    public static int RIGHT = MouseEvent.BUTTON3;

    public static MouseDriver getDriver(){
        return mouseDriver;
    }

    public static MousePathGenerator getMousePathGenerator() {
        return mousePathGenerator;
    }

    public static MouseFuture click(ScreenLocation screenLocation, int mouseButton){
        return click(new ScreenPolygon(screenLocation), mouseButton);
    }

    public static MouseFuture click(ScreenPolygon screenTarget, int mouseButton){
        return getDriver().execute(new MouseFuture()
                .setScreenTaget(screenTarget)
                .setMouseButton(mouseButton));
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
        return move(new ScreenPolygon(screenLocation));
    }

    public static void setMouseDriver(MouseDriver mouseDriver) {
        Mouse.mouseDriver = mouseDriver;
    }

    public static void setMousePathGenerator(MousePathGenerator mousePathGenerator) {
        Mouse.mousePathGenerator = mousePathGenerator;
    }

    public static MouseFuture move(ScreenPolygon screenTarget) {
        return getDriver().execute(new MouseFuture().setScreenTaget(screenTarget));
    }
}
