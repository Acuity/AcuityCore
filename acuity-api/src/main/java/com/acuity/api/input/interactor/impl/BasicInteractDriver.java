package com.acuity.api.input.interactor.impl;

import com.acuity.api.input.direct.mouse.Mouse;
import com.acuity.api.input.interactor.InteractionDriver;
import com.acuity.api.rs.interfaces.Interactive;
import com.acuity.api.rs.utils.ActionResult;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocationShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Zachary Herridge on 7/11/2017.
 */
public class BasicInteractDriver implements InteractionDriver {

    private static final Logger logger = LoggerFactory.getLogger(BasicInteractDriver.class);

    @Override
    public ActionResult interact(Interactive interactive, String action) {
        ScreenLocationShape screenLocationShape = interactive.projectToScreen().orElse(null);
        if (screenLocationShape != null){
            ScreenLocation screenLocation = screenLocationShape.randomLocation();
            if (screenLocation != null){
                Mouse.click(screenLocation);
                return ActionResult.SUCCESS;
            }
        }

        logger.debug("Failed to find interact point.");
        return ActionResult.FAILURE;
    }
}
