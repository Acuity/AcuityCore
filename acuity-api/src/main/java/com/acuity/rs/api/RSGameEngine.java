package com.acuity.rs.api;

import com.acuity.api.rs.wrappers.engine.GameEngine;
import java.awt.Canvas;

//Generated

public interface RSGameEngine extends java.lang.Runnable, java.awt.event.FocusListener, java.awt.event.WindowListener {

    Canvas getCanvas();

    GameEngine getWrapper();
}
