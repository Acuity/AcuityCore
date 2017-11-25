package com.acuity.api.input.direct.mouse;

import com.acuity.api.rs.utils.Delay;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

public class MouseFuture {

    private ScreenLocation screenLocation;
    private int mouseButton = -1;

    private boolean complete = false;
    private boolean canceled = false;

    public ScreenLocation getScreenLocation() {
        return screenLocation;
    }

    public MouseFuture setScreenLocation(ScreenLocation screenLocation) {
        this.screenLocation = screenLocation;
        return this;
    }

    public int getMouseButton() {
        return mouseButton;
    }

    public MouseFuture setMouseButton(int mouseButton) {
        this.mouseButton = mouseButton;
        return this;
    }

    public boolean isComplete() {
        return complete;
    }

    public MouseFuture setComplete(boolean complete) {
        this.complete = complete;
        return this;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public MouseFuture setCanceled(boolean canceled) {
        this.canceled = canceled;
        return this;
    }

    public MouseFuture sleep(){
        while (!isComplete() && !isCanceled()) Delay.delay(Delay.getDelayUntilSleepMS());
        return this;
    }
}
