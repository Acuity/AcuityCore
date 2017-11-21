package com.acuity.api.rs.wrappers.common.locations.screen;

import java.awt.*;

/**
 * Created by Zachary Herridge on 7/5/2017.
 */
public class ScreenLocation {

    protected int x, y;

    public ScreenLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static ScreenLocation fromPoint(Point point){
        return new ScreenLocation(point.x, point.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ScreenLocation increment(int xOff, int yOff){
        this.x += xOff;
        this.y += yOff;
        return this;
    }

    public ScreenLocation transform(int xOff, int yOff){
        return new ScreenLocation(x + xOff, y + yOff);
    }

    public int[] toArray() {
        return new int[]{x, y};
    }

    public Point toPoint(){
        return new Point(x, y);
    }
}
