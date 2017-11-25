package com.acuity.api.input.direct.mouse.pathing;

import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

public class MousePathMovement {

    private ScreenLocation movement;
    private int sleepMS;

    public ScreenLocation getMovement() {
        return movement;
    }

    public MousePathMovement setMovement(ScreenLocation movement) {
        this.movement = movement;
        return this;
    }

    public int getSleepMS() {
        return sleepMS;
    }

    public MousePathMovement setSleepMS(int sleepMS) {
        this.sleepMS = sleepMS;
        return this;
    }
}
