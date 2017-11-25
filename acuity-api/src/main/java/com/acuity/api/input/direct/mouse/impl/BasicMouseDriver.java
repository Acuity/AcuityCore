package com.acuity.api.input.direct.mouse.impl;

import com.acuity.api.AcuityInstance;
import com.acuity.api.input.direct.mouse.Mouse;
import com.acuity.api.input.direct.mouse.MouseDriver;
import com.acuity.api.input.direct.mouse.MouseFuture;
import com.acuity.api.input.direct.mouse.pathing.MousePathMovement;
import com.acuity.api.rs.utils.Delay;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Zachary Herridge on 7/11/2017.
 */
public class BasicMouseDriver implements MouseDriver {

    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private Executor executor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, queue);

    private Runnable convert(MouseFuture mouseFuture){
        return () -> {
            if (mouseFuture.isCanceled()) return;
            List<MousePathMovement> mousePath = Mouse.getMousePathGenerator().generatePath(Mouse.getPosition(), mouseFuture.getScreenTaget());

            for (MousePathMovement mousePathMovement : mousePath) {
                if (mouseFuture.isCanceled()) return;
                AcuityInstance.getAppletManager().getMouseMiddleMan().dispatchMove(mousePathMovement.getMovement());
                if (mouseFuture.isCanceled()) return;
                Delay.delay(mousePathMovement.getSleepMS());
            }

            if (mouseFuture.isCanceled()) return;
            if (mouseFuture.getMouseButton() != -1) AcuityInstance.getAppletManager().getMouseMiddleMan().dispatchClick(mousePath.get(mousePath.size() - 1).getMovement(), mouseFuture.getMouseButton());
            mouseFuture.setComplete(true);
        };
    }

    @Override
    public MouseFuture execute(MouseFuture mouseFuture) {
        executor.execute(convert(mouseFuture));
        return mouseFuture;
    }
}
