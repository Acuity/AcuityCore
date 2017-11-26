package com.acuity.api.rs.utils;

import com.acuity.api.AcuityInstance;
import com.acuity.api.input.direct.mouse.Mouse;
import com.acuity.api.input.direct.mouse.MouseFuture;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenPolygon;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenRectangle;

import java.util.*;
import java.util.List;

/**
 * Created by Zachary Herridge on 7/11/2017.
 */
public class ContextMenu {

    public static List<String> getRows(){
        int rowCount = getRowCount();
        String[] contextMenuActions = AcuityInstance.getClient().getContextMenuActions();
        String[] contextMenuTargets = AcuityInstance.getClient().getContextMenuTargets();

        List<String> items = new ArrayList<>();
        for (int i = rowCount - 1; i >= 0; i--) {
            items.add(FormatUtil.format(contextMenuActions[i] + " " + FormatUtil.format(contextMenuTargets[i])));
        }
        return items;
    }

    public static String getCurrentHotAction(){
        if (isOpen()){
            ScreenLocation position = Mouse.getPosition();
            if (position != null) {
                List<String> collect = getRows();
                for (int i = 0; i < collect.size(); i++) {
                    Boolean contains = getRowBounds(i).map(screenLocationShape -> screenLocationShape.contains(position)).orElse(false);
                    if (contains) return collect.get(i);
                }
            }
        }
        else {
            return getRows().stream().findFirst().orElse("Cancel");
        }

        return "Cancel";
    }

    public static boolean isOpen(){
        return AcuityInstance.getClient().isMenuOpen();
    }

    public static int getRowCount(){
        return AcuityInstance.getClient().getContextMenuRowCount();
    }

    public static Optional<ScreenLocation> getLocation(){
        if (isOpen()) return Optional.of(new ScreenLocation(AcuityInstance.getClient().getContextMenuX(), AcuityInstance.getClient().getContextMenuY()));
        return Optional.empty();
    }

    public static int getWidth(){
        return AcuityInstance.getClient().getContextMenuWidth();
    }

    public static int getHeight(){
        return AcuityInstance.getClient().getContextMenuHeight();
    }

    public static Optional<ScreenPolygon> getRowBounds(int index) {
        return getLocation().map(screenLocation -> new ScreenRectangle(screenLocation.transform(0, (19 + (index * 15))), getWidth(), 14));
    }

    public static Optional<ScreenRectangle> getBounds(){
        return getLocation().map(screenLocation -> new ScreenRectangle(screenLocation, getWidth(), getHeight()));
    }

    public static boolean close(){
        getBounds().ifPresent(bounds -> {
            ScreenLocation targetLocation = new ScreenLocation(
                    Projection.GAME_SCREEN.getRoot().getX() + Random.nextInt(0, Projection.GAME_SCREEN.getWidth()),
                    Projection.GAME_SCREEN.getRoot().getY()
            );

            MouseFuture mouseFuture = new MouseFuture() {
                @Override
                public void onStep() {
                    if (!isOpen()) setCanceled(true);
                }
            }.setScreenTaget(new ScreenPolygon(targetLocation));

            Mouse.getDriver().execute(mouseFuture);
        });
        return isOpen();
    }

    public static Optional<ScreenPolygon> getScreenTarget(String action) {
        List<String> collect = getRows();
        for (int i = 0; i < collect.size(); i++) {
            if (collect.get(i).toLowerCase().startsWith(action.toLowerCase())) return getScreenTarget(i);
        }
        return Optional.empty();
    }

    public static Optional<ScreenPolygon> getScreenTarget(int index) {
        return getLocation().map(screenLocation -> new ScreenRectangle(screenLocation.transform(5, (19 + (index * 15)) + 5), getWidth() - 10, 14 - 10));
    }
}
