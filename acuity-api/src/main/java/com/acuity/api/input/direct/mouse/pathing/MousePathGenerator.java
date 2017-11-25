package com.acuity.api.input.direct.mouse.pathing;

import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenPolygon;

import java.util.List;

public interface MousePathGenerator {

    default List<MousePathMovement> generatePath(ScreenLocation start, ScreenPolygon target){
        return generatePath(start, target, target.randomLocation());
    }

    List<MousePathMovement> generatePath(ScreenLocation start, ScreenPolygon target, ScreenLocation end);

}
