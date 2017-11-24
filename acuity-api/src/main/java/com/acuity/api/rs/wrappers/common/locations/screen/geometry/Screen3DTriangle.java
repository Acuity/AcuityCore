package com.acuity.api.rs.wrappers.common.locations.screen.geometry;

import com.acuity.api.rs.wrappers.common.locations.screen.Screen3DLocation;

public class Screen3DTriangle  {

    private final Screen3DLocation p1;
    private final Screen3DLocation p2;
    private final Screen3DLocation p3;

    public Screen3DTriangle(Screen3DLocation p1, Screen3DLocation p2, Screen3DLocation p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Screen3DLocation getP1() {
        return p1;
    }

    public Screen3DLocation getP2() {
        return p2;
    }

    public Screen3DLocation getP3() {
        return p3;
    }
}
