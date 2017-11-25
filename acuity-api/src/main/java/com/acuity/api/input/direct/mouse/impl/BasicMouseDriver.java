package com.acuity.api.input.direct.mouse.impl;

import com.acuity.api.AcuityInstance;
import com.acuity.api.input.direct.mouse.MouseDriver;
import com.acuity.api.input.direct.mouse.MouseFuture;

import java.util.concurrent.*;

/**
 * Created by Zachary Herridge on 7/11/2017.
 */
public class BasicMouseDriver implements MouseDriver {

    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private Executor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue);

    private Runnable convert(MouseFuture mouseFuture){
        return () -> {
            if (mouseFuture.isCanceled()) return;
            AcuityInstance.getAppletManager().getMouseMiddleMan().dispatchMove(mouseFuture.getScreenLocation());
            if (mouseFuture.isCanceled()) return;
            if (mouseFuture.getMouseButton() != -1) AcuityInstance.getAppletManager().getMouseMiddleMan().dispatchClick(mouseFuture.getScreenLocation(), mouseFuture.getMouseButton());
            mouseFuture.setComplete(true);
        };
    }

    @Override
    public MouseFuture queue(MouseFuture mouseFuture) {
        executor.execute(convert(mouseFuture));
        return mouseFuture;
    }
}
