package com.acuity.api.input.direct.mouse.pathing.impl;

import com.acuity.api.input.direct.mouse.pathing.MousePathGenerator;
import com.acuity.api.input.direct.mouse.pathing.MousePathMovement;
import com.acuity.api.rs.utils.Random;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenPolygon;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BasicMousePathGenerator implements MousePathGenerator {

    private static final int MS_PER_BIT = 50;

    @Override
    public List<MousePathMovement> generatePath(ScreenLocation start, ScreenPolygon target, ScreenLocation end) {
        double distance = start.distanceTo(end);
        double size = Math.max(target.size(), 10);
        long timeToTarget = fittsLaw(distance, size);

        int xDif = end.getX() - start.getX();
        int yDif = end.getY() - start.getY();

        double steps = timeToTarget / MS_PER_BIT;
        int xStep = (int) Math.round(xDif / steps);
        int yStep = (int) Math.round(yDif / steps);

        List<MousePathMovement> path = new ArrayList<>();
        ScreenLocation lastLocation = start;
        for (int i = 0; i < steps - 1; i++) {
            lastLocation = lastLocation.transform(xStep, yStep);
            path.add(new MousePathMovement()
                    .setMovement(lastLocation)
                    .setSleepMS(MS_PER_BIT + Random.nextInt(-5, 5))
            );
        }

        path.add(new MousePathMovement().setMovement(end).setSleepMS(MS_PER_BIT));

        return path;
    }

    private long fittsLaw(double targetDist, double targetSize) {
        double randomMSPerBit = MS_PER_BIT * 0.8 + (0.4 * (ThreadLocalRandom.current().nextDouble() * MS_PER_BIT));
        return (long) (randomMSPerBit * Math.log10(targetDist / targetSize + 1) / Math.log10(2));
    }
}
