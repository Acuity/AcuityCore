package com.acuity.api.input.direct.mouse;

/**
 * Created by Zachary Herridge on 7/11/2017.
 */
public interface MouseDriver {

    MouseFuture queue(MouseFuture mouseFuture);
}
