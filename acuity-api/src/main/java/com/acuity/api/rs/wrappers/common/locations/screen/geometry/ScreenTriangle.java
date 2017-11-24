package com.acuity.api.rs.wrappers.common.locations.screen.geometry;

import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

public class ScreenTriangle extends ScreenPolygon {

    private final ScreenLocation p1;
    private final ScreenLocation p2;
    private final ScreenLocation p3;

    public ScreenTriangle(ScreenLocation p1, ScreenLocation p2, ScreenLocation p3) {
        super(p1, p2, p3);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public ScreenLocation getP1() {
        return p1;
    }

    public ScreenLocation getP2() {
        return p2;
    }

    public ScreenLocation getP3() {
        return p3;
    }
}
