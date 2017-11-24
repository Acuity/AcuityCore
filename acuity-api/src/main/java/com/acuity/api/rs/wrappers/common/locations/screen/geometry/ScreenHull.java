package com.acuity.api.rs.wrappers.common.locations.screen.geometry;

import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

import java.util.Collection;

public class ScreenHull extends ScreenPolygon{

    private double coverage = 1;

    public ScreenHull(ScreenLocation... locations) {
        super(locations);
    }

    public ScreenHull(Collection<ScreenLocation> locations) {
        super(locations);
    }

    public double getCoverage() {
        return coverage;
    }

    public ScreenHull setCoverage(double coverage) {
        this.coverage = coverage;
        return this;
    }
}
