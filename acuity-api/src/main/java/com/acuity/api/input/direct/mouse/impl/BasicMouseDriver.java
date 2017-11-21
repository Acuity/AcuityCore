package com.acuity.api.input.direct.mouse.impl;

import com.acuity.api.AcuityInstance;
import com.acuity.api.input.direct.mouse.MouseDriver;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

/**
 * Created by Zachary Herridge on 7/11/2017.
 */
public class BasicMouseDriver implements MouseDriver {

    @Override
    public void click(ScreenLocation screenLocation, boolean leftClick) {
        AcuityInstance.getAppletManager().getMouseMiddleMan().dispatchClick(screenLocation.getX(), screenLocation.getY(), leftClick);
    }

    @Override
    public void move(ScreenLocation screenLocation) {

    }
}
