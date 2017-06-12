package com.acuity.api.script.impl;

/**
 * Created by MadDev on 6/11/17.
 */
public abstract class AcuityScript implements Loopable {

    private ScriptState state = ScriptState.NO_SCRIPT;

    public void execute() {
        final Thread t = new Thread(script);
        t.setName("AcuityScriptRunner");
        t.start();
        state = ScriptState.RUNNING;
    }

    private Runnable script = () -> {
        while (state != ScriptState.NO_SCRIPT) {
            try {
                doLoop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private void doLoop() throws InterruptedException {
        synchronized (this) {
            if (state == ScriptState.PAUSED) {
                Thread.sleep(50);
                return;
            }
            try {
                loop();
            } catch (Exception e) {
                e.printStackTrace();
                state = ScriptState.NO_SCRIPT;
            }
        }
    }

    public void stop() {
        state = ScriptState.NO_SCRIPT;
    }

    public void pause() {
        state = ScriptState.PAUSED;
    }

    public void resume() {
        state = ScriptState.RUNNING;
    }

    public boolean isPaused() {
        return state == ScriptState.PAUSED;
    }

    public ScriptState getState() {
        return state;
    }
}
