package com.acuity.rs.api;

import com.acuity.api.rs.wrappers.engine.GameEngine;
import java.awt.Canvas;
import java.awt.event.FocusListener;
import java.awt.event.WindowListener;

//Generated

public interface RSGameEngine extends Runnable, FocusListener, WindowListener {

    Canvas getCanvas();

    GameEngine getWrapper();
}
