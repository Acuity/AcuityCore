package com.acuity.api.rs.wrappers.common;

import com.acuity.api.rs.utils.Scene;

/**
 * Created by Zach on 7/1/2017.
 */
public class StrictLocation {

    private int x, y, plane;
    private int baseX, baseY;

    public StrictLocation(int x, int y, int plane) {
        this(x, y, plane, Scene.getBaseX(), Scene.getBaseY());
    }

    public StrictLocation(int x, int y, int plane, int baseX, int baseY) {
        this.x = x;
        this.y = y;
        this.plane = plane;
        this.baseX = baseX;
        this.baseY = baseY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public SceneLocation getSceneLocation(){
        return new SceneLocation(x / 128, y / 128, plane, baseX, baseY);
    }

    public WorldLocation getWorldLocation(){
        return getSceneLocation().getWorldLocation();
    }
}
