package com.acuity.api.rs.interfaces;

import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocationShape;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by Zachary Herridge on 7/10/2017.
 */
public interface Clickable {

    Supplier<Optional<ScreenLocationShape>> EMPTY_SUPPLIER = Optional::empty;

    Supplier<ScreenLocationShape<?>> getScreenTargetSupplier();
}
