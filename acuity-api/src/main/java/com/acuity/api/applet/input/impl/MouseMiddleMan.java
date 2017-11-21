package com.acuity.api.applet.input.impl;

import com.acuity.api.Events;
import com.acuity.api.applet.input.InputMiddleMan;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Zach on 6/14/2017.
 */
public class MouseMiddleMan implements InputMiddleMan {

    private static Logger logger = LoggerFactory.getLogger(MouseMiddleMan.class);

    private Component component;
    private boolean acceptingUserInput = true;

    private MMouseListener output = new MMouseListener();

    private Point mousePosition;

    public void dispatchClick(ScreenLocation screenLocation, boolean left) {
        output.dispatch(new MouseEvent(component, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis()+ 10, 0, screenLocation.getX(), screenLocation.getY(), 1, false, left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3));
        output.dispatch(new MouseEvent(component, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis() + 20, 0, screenLocation.getX(), screenLocation.getY(), 1, false, left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3));
    }

    public void dispatchMove(ScreenLocation screenLocation){
        output.dispatch(new MouseEvent(component, MouseEvent.MOUSE_MOVED, System.currentTimeMillis()+ 10, 0, screenLocation.getX(), screenLocation.getY(), 0, false));
    }

    @Override
    public boolean insertInto(Component component) {
        this.component = component;

        MouseListener[] mouseListeners = component.getMouseListeners();
        for (MouseListener mouseListener : mouseListeners) {
            component.removeMouseListener(mouseListener);
            logger.debug("Removed MouseListener {} from component.", mouseListener);
        }
        output.setMouseListeners(mouseListeners);

        MouseMotionListener[] mouseMotionListeners = component.getMouseMotionListeners();
        for (MouseMotionListener mouseMotionListener : mouseMotionListeners) {
            component.removeMouseMotionListener(mouseMotionListener);
            logger.debug("Removed MouseMotionListener {} from component.", mouseMotionListener);
        }
        output.setMouseMotionListeners(mouseMotionListeners);

        MouseWheelListener[] mouseWheelListeners = component.getMouseWheelListeners();
        for (MouseWheelListener mouseWheelListener : mouseWheelListeners) {
            component.removeMouseWheelListener(mouseWheelListener);
            logger.debug("Removed MouseWheelListener {} from component.", mouseWheelListener);
        }
        output.setMouseWheelListeners(mouseWheelListeners);

        component.addMouseListener(output);
        logger.debug("Added MMouseListener as MouseListener to component {}.", component);
        component.addMouseMotionListener(output);
        logger.debug("Added MMouseListener as MouseMotionListener to component {}.", component);
        component.addMouseWheelListener(output);
        logger.debug("Added MMouseListener as MouseWheelListener to component {}.", component);

        logger.info("Successfully middle-manned mouse of component {} with {}.", component, output);
        return true;
    }

    @Override
    public boolean isAcceptingUserInput() {
        return acceptingUserInput;
    }

    @Override
    public void setAcceptingUserInput(boolean acceptingUserInput) {
        this.acceptingUserInput = acceptingUserInput;
    }

    public Optional<Point> getMousePosition() {
        return Optional.ofNullable(mousePosition);
    }

    public MMouseListener getOutput() {
        return output;
    }

    public class MMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener{

        private MouseListener[] mouseListeners;
        private MouseMotionListener[] mouseMotionListeners;
        private MouseWheelListener[] mouseWheelListeners;

        public void dispatch(MouseWheelEvent e){
            switch (e.getID()){
                case MouseEvent.MOUSE_WHEEL:
                    for (MouseWheelListener mouseListener : mouseWheelListeners) mouseListener.mouseWheelMoved(e);
                    break;
                default:
                    logger.warn("Attempted to dispatch unknown MouseWheelEvent {}.", e);
            }
        }

        public void dispatch(MouseEvent e){
            if (e.isConsumed()) return;
            mousePosition = e.getPoint();
            switch (e.getID()){
                case MouseEvent.MOUSE_CLICKED:
                    for (MouseListener mouseListener : mouseListeners) mouseListener.mouseClicked(e);
                    break;
                case MouseEvent.MOUSE_RELEASED:
                    for (MouseListener mouseListener : mouseListeners) mouseListener.mouseReleased(e);
                    break;
                case MouseEvent.MOUSE_ENTERED:
                    for (MouseListener mouseListener : mouseListeners) mouseListener.mouseEntered(e);
                    break;
                case MouseEvent.MOUSE_EXITED:
                    for (MouseListener mouseListener : mouseListeners) mouseListener.mouseExited(e);
                    break;
                case MouseEvent.MOUSE_PRESSED:
                    for (MouseListener mouseListener : mouseListeners) mouseListener.mousePressed(e);
                    break;
                case MouseEvent.MOUSE_MOVED:
                    for (MouseMotionListener mouseListener : mouseMotionListeners) mouseListener.mouseMoved(e);
                    break;
                case MouseEvent.MOUSE_DRAGGED:
                    for (MouseMotionListener mouseListener : mouseMotionListeners) mouseListener.mouseDragged(e);
                    break;
                default:
                    logger.error("Failed to dispatch unknown MouseEvent {}.", e);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            mousePosition = e.getPoint();
            Events.getAcuityEventBus().post(e);
            if (isAcceptingUserInput()) dispatch(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePosition = e.getPoint();
            Events.getAcuityEventBus().post(e);
            if (isAcceptingUserInput()) dispatch(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePosition = e.getPoint();
            Events.getAcuityEventBus().post(e);
            if (isAcceptingUserInput()) dispatch(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            mousePosition = e.getPoint();
            Events.getAcuityEventBus().post(e);
            if (isAcceptingUserInput()) dispatch(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mousePosition = e.getPoint();
            Events.getAcuityEventBus().post(e);
            if (isAcceptingUserInput()) dispatch(e);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mousePosition = e.getPoint();
            Events.getAcuityEventBus().post(e);
            if (isAcceptingUserInput()) dispatch(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mousePosition = e.getPoint();
            Events.getAcuityEventBus().post(e);
            if (isAcceptingUserInput()) dispatch(e);
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            mousePosition = e.getPoint();
            Events.getAcuityEventBus().post(e);
            if (isAcceptingUserInput()) dispatch(e);
        }

        public void setMouseListeners(MouseListener[] mouseListeners) {
            this.mouseListeners = mouseListeners;
        }

        public void setMouseMotionListeners(MouseMotionListener[] mouseMotionListeners) {
            this.mouseMotionListeners = mouseMotionListeners;
        }

        public void setMouseWheelListeners(MouseWheelListener[] mouseWheelListeners) {
            this.mouseWheelListeners = mouseWheelListeners;
        }

        @Override
        public String toString() {
            return "MMouseListener{" +
                    "mouseListeners=" + Arrays.toString(mouseListeners) +
                    ", mouseMotionListeners=" + Arrays.toString(mouseMotionListeners) +
                    ", mouseWheelListeners=" + Arrays.toString(mouseWheelListeners) +
                    '}';
        }
    }
}
