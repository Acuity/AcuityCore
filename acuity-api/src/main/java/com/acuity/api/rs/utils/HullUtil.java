package com.acuity.api.rs.utils;

import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenHull;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HullUtil {

    public static ScreenHull convexHull(Collection<ScreenLocation> screenLocations, boolean confineToGameScreen) {
        if (screenLocations.size() < 3) {
            return null;
        }

        List<ScreenLocation> hull = new ArrayList<>();
        int filtered = 0;
        ScreenLocation left = findLeftMost(screenLocations);
        ScreenLocation current = left;
        do {
            if (!confineToGameScreen || Camera.isVisible(current)) {
                hull.add(current);
            }
            else {
                filtered++;
            }

            ScreenLocation next = null;

            for (ScreenLocation screenLocation : screenLocations) {
                if (next == null) {
                    next = screenLocation;
                    continue;
                }

                int crossProduct = crossProduct(current, screenLocation, next);
                if (crossProduct > 0 || (crossProduct == 0 && current.distanceTo(screenLocation) > current.distanceTo(next))) {
                    next = screenLocation;
                }
            }

            assert next != null;

            current = next;
        }
        while (current != left);

        int size = hull.size();
        return new ScreenHull(hull).setCoverage((double) size / ((double) size + (double) filtered));
    }

    private static ScreenLocation findLeftMost(Collection<ScreenLocation> screenLocations) {
        ScreenLocation left = null;

        for (ScreenLocation screenLocation : screenLocations) {
            if (left == null || screenLocation.getX() < left.getX()) {
                left = screenLocation;
            } else if (screenLocation.getX() == left.getX() && screenLocation.getY() < left.getY()) {
                left = screenLocation;
            }
        }

        return left;
    }

    private static int crossProduct(ScreenLocation p, ScreenLocation q, ScreenLocation r) {
        return (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());
    }
}
