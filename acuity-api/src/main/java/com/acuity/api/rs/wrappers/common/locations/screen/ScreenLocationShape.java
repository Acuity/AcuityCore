package com.acuity.api.rs.wrappers.common.locations.screen;

import com.acuity.api.rs.utils.Random;

import java.awt.*;
import java.util.Collection;

/**
 * Created by Zachary Herridge on 7/10/2017.
 */
public class ScreenLocationShape {

    private ScreenLocation[] locations;
    private Polygon polygon;

    public ScreenLocationShape(ScreenLocation... locations) {
        this.locations = locations;
    }

    public ScreenLocationShape(Collection<ScreenLocation> locations) {
        this.locations = (ScreenLocation[]) locations.toArray();
    }

    public ScreenLocation[] getLocations() {
        return locations;
    }

    public ScreenLocation randomLocation(){
        return Random.nextLocation(this);
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
        return contains(location.getX(), location.getY());
    }

    public boolean contains(int x, int y) {
        return getPolygon().contains(x, y);
    }
}
