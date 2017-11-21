package com.acuity.api.rs.utils;

import com.acuity.api.AcuityInstance;
import com.acuity.api.input.direct.mouse.Mouse;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocationShape;
import com.google.common.collect.Streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Zachary Herridge on 7/11/2017.
 */
public class ContextMenu {

    public static Stream<String> streamActions(){
        return Arrays.stream(AcuityInstance.getClient().getContextMenuActions(), 0, getRowCount())
                .filter(Objects::nonNull)
                .sorted(Collections.reverseOrder());
    }

    public static Stream<String> streamTargets(){
        return Arrays.stream(AcuityInstance.getClient().getContextMenuTargets(), 0, getRowCount())
                .filter(Objects::nonNull)
                .sorted(Collections.reverseOrder());

    }

    public static Stream<String> streamChildren(){
        return Streams.zip(streamActions(), streamTargets(), (action, target) -> FormatUtil.format(action) + " " + FormatUtil.format(target));
    }

    public static String getCurrentHotAction(){
        if (isOpen()){
            ScreenLocation position = Mouse.getPosition();
            if (position != null) {
                List<String> collect = streamChildren().collect(Collectors.toList());
                for (int i = 0; i < collect.size(); i++) {
                    Boolean contains = getBounds(i).map(screenLocationShape -> screenLocationShape.contains(position)).orElse(false);
                    if (contains) return collect.get(i);
                }
            }
        }
        else {
            return streamChildren().findFirst().orElse("Cancel");
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

    public static Optional<ScreenLocationShape> getBounds(int index) {
        return getLocation().map(screenLocation -> {
            ScreenLocation low = screenLocation.transform(0, 18 + index * 15 + 1);
            ScreenLocation high = screenLocation.transform(getWidth(), 15);
            return new ScreenLocationShape(low, high);
        });
    }
}
