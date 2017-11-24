package com.acuity.api.rs.interfaces;

import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenPolygon;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by Zachary Herridge on 7/10/2017.
 */
public interface Projectable {

    Supplier<ScreenPolygon> getProjectionSupplier();

    default Optional<ScreenPolygon> projectToScreen(){
        return Optional.ofNullable(getProjectionSupplier().get());
    }
}
