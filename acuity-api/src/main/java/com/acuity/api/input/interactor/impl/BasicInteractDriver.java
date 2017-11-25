package com.acuity.api.input.interactor.impl;

import com.acuity.api.input.direct.mouse.Mouse;
import com.acuity.api.input.interactor.InteractionDriver;
import com.acuity.api.rs.interfaces.Interactive;
import com.acuity.api.rs.utils.ActionResult;
import com.acuity.api.rs.utils.ContextMenu;
import com.acuity.api.rs.utils.Delay;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenPolygon;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Zachary Herridge on 7/11/2017.
 */
public class BasicInteractDriver implements InteractionDriver {

    private static final Logger logger = LoggerFactory.getLogger(BasicInteractDriver.class);

    @Override
    public ActionResult interact(Interactive interactive, String action) {
        ContextMenu.close();
        ScreenPolygon screenPolygon = interactive.projectToScreen().orElse(null);
        if (screenPolygon != null){
            ScreenLocation screenLocation = screenPolygon.randomLocation();
            if (screenLocation != null){
                if (action.equalsIgnoreCase("")){
                    Mouse.click(screenLocation,  true).sleep();
                }
                else {
                    Mouse.click(screenLocation, false).sleep();
                    Delay.delayUntil(ContextMenu::isOpen, 600);
                    ContextMenu.getScreenTarget(action).map(ScreenPolygon::randomLocation).ifPresent(menuTarget -> {
                        Mouse.move(menuTarget).sleep();
                        Delay.delay(50, 90);
                        if (Strings.nullToEmpty(ContextMenu.getCurrentHotAction()).toLowerCase().startsWith(action.toLowerCase())){
                            Mouse.click(menuTarget, true).sleep();
                        }
                    });
                }

                return ActionResult.SUCCESS;
            }
        }

        logger.debug("Failed to find interact point.");
        return ActionResult.FAILURE;
    }
}
