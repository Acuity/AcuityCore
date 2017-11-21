package com.acuity.api.rs.wrappers.common.locations.screen;

/**
 * Created by Zachary Herridge on 7/10/2017.
 */
public class ScreenLocationShape {

    private ScreenLocation[] locations;

    public ScreenLocationShape(ScreenLocation... locations) {
        this.locations = locations;
    }

    public ScreenLocation[] getLocations() {
        return locations;
    }

    public boolean contains(ScreenLocation location){
        return false;
    }
}
