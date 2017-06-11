package com.acuity.rs.api;

//Generated

import java.awt.Canvas;
import java.awt.event.FocusListener;
import java.awt.event.WindowListener;

public interface RSGameEngine extends Runnable, FocusListener, WindowListener {

    Canvas getCanvas();
}
