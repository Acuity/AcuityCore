package com.acuity.api.rs.wrappers.common.locations.screen;

import java.awt.*;

/**
 * Created by Zachary Herridge on 7/10/2017.
 */
public class ScreenLocationShape {

    private ScreenLocation[] locations;
    private Polygon polygon;

    public ScreenLocationShape(ScreenLocation... locations) {
        this.locations = locations;
    }

    public ScreenLocation[] getLocations() {
        return locations;
    }

    public Polygon getPolygon(){
        if (polygon == null){
            polygon = new Polygon();
            for (ScreenLocation location : locations) {
                polygon.addPoint(location.getX(), location.getY());
            }
        }
        return polygon;
    }

    public boolean contains(ScreenLocation location){
        return getPolygon().contains(location.toPoint());
    }
}
