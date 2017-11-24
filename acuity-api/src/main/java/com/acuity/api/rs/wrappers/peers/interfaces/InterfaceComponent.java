package com.acuity.api.rs.wrappers.peers.interfaces;

import com.acuity.api.AcuityInstance;
import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.interfaces.Interactive;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenPolygon;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenRectangle;
import com.acuity.api.rs.wrappers.peers.structures.Node;
import com.acuity.rs.api.RSInterfaceComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by MadDev, June 10, 2017
 */
public class InterfaceComponent extends Node implements Interactive {

    private static final Logger logger = LoggerFactory.getLogger(InterfaceComponent.class);

    private final RSInterfaceComponent rsInterfaceComponent;

    @ClientInvoked
    public InterfaceComponent(RSInterfaceComponent peer) {
        super(peer);
        this.rsInterfaceComponent = peer;
    }

    // TODO: 6/12/2017 Can this be null?
    public InterfaceComponent[] getComponents() {
        logger.trace("Wrapping RSInterfaceComponent[] from RSInterfaceComponent.");
        return Arrays.stream(rsInterfaceComponent.getComponents())
                .map(child -> child != null ? child.getWrapper() : null)
                .toArray(InterfaceComponent[]::new);
    }

    @Override
    public List<String> getActions() {
        return Arrays.asList(rsInterfaceComponent.getActions());
    }

    @Override
    public Supplier<ScreenPolygon> getProjectionSupplier() {
        return () -> {
            int x = 0;
            int y = 0;

            InterfaceComponent currentInterface;
            for (currentInterface = this; currentInterface.getParent().isPresent(); currentInterface = currentInterface.getParent().get()) {
                x += currentInterface.getRelativeX();
                y += currentInterface.getRelativeY();
                x -= currentInterface.getScrollX();
                y -= currentInterface.getInsetY();
            }

            int[] interfacePositionX = AcuityInstance.getClient().getInterfacePositionX();
            int[] interfacePositionY = AcuityInstance.getClient().getInterfacePositionY();

            int boundsIndex = currentInterface.getBoundsIndex();
            if (boundsIndex != -1) {
                x += interfacePositionX[boundsIndex];
                y += interfacePositionY[boundsIndex];

                if (currentInterface.getType() > 0) {
                    x += currentInterface.getRelativeX();
                    y += currentInterface.getRelativeY();
                }
            }
            else {
                x += currentInterface.getRelativeX();
                y += currentInterface.getRelativeY();
            }

            return new ScreenRectangle(x, y, getWidth(), getHeight());
        };
    }

    public Optional<InterfaceComponent> getParent() {
        return Optional.ofNullable(rsInterfaceComponent.getParent()).map(RSInterfaceComponent::getWrapper);
    }

    public int getBorderThickness() {
        return rsInterfaceComponent.getBorderThickness();
    }

    public int getBoundsIndex() {
        return rsInterfaceComponent.getBoundsIndex();
    }

    public int getComponentIndex() {
        return rsInterfaceComponent.getComponentIndex();
    }

    public int getContentType() {
        return rsInterfaceComponent.getType();
    }

    public int getFondID() {
        return rsInterfaceComponent.getFondID();
    }

    public int getHeight() {
        return rsInterfaceComponent.getHeight();
    }

    public int getInsetY() {
        return rsInterfaceComponent.getInsetY();
    }

    public int getItemId() {
        return rsInterfaceComponent.getItemID();
    }

    public int[] getItemIds() {
        return rsInterfaceComponent.getItemIDs();
    }

    public int getItemStackSize() {
        return rsInterfaceComponent.getItemStackSize();
    }

    public int[] getItemStackSizes() {
        return rsInterfaceComponent.getItemStackSizes();
    }

    public int getModelId() {
        return rsInterfaceComponent.getModelID();
    }

    public int getModelType() {
        return rsInterfaceComponent.getModelType();
    }

    public int getRelativeX() {
        return rsInterfaceComponent.getRelativeX();
    }

    public int getRelativeY() {
        return rsInterfaceComponent.getRelativeY();
    }

    public int getRotationX() {
        return rsInterfaceComponent.getRotationX();
    }

    public int getRotationY() {
        return rsInterfaceComponent.getRotationY();
    }

    public int getRotationZ() {
        return rsInterfaceComponent.getRotationZ();
    }

    public int getScrollHeight() {
        return rsInterfaceComponent.getScrollHeight();
    }

    public int getScrollWidth() {
        return rsInterfaceComponent.getScrollWidth();
    }

    public int getScrollX() {
        return rsInterfaceComponent.getScrollX();
    }

    public Optional<String> getSelectedAction() {
        return Optional.ofNullable(rsInterfaceComponent.getSelectedAction());
    }

    public int getShadowColor() {
        return rsInterfaceComponent.getShadowColor();
    }

    public int getSpriteID() {
        return rsInterfaceComponent.getSpriteID();
    }

    public String[] getTableActions() {
        return rsInterfaceComponent.getTableActions();
    }

    public Optional<String> getText() {
        return Optional.ofNullable(rsInterfaceComponent.getText());
    }

    public String getNullSafeText() {// TODO: 6/12/2017 Are we returning "null" or "" for nullsafe returns?
        return rsInterfaceComponent.getText() == null ? "" : rsInterfaceComponent.getText();
    }

    public int getTextColor() {
        return rsInterfaceComponent.getTextColor();
    }

    public int getType() {
        return rsInterfaceComponent.getType();
    }

    public int getUID() {
        return rsInterfaceComponent.getUID();
    }

    public int getWidth() {
        return rsInterfaceComponent.getWidth();
    }

    public int getXPadding() {
        return rsInterfaceComponent.getXPadding();
    }

    public int getYPadding() {
        return rsInterfaceComponent.getYPadding();
    }

    public boolean isFlippedHorizontally() {
        return rsInterfaceComponent.isFlippedHorizontally();
    }

    public boolean isFlippedVertically() {
        return rsInterfaceComponent.isFlippedVertically();
    }

    public boolean isHidden() {
        return rsInterfaceComponent.isHidden();
    }

    public boolean isVisible() {
        return !rsInterfaceComponent.isHidden();
    }

    public RSInterfaceComponent getRsComponent() {
        return rsInterfaceComponent;
    }
}
