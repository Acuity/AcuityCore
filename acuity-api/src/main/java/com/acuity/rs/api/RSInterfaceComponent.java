package com.acuity.rs.api;

import com.acuity.api.rs.wrappers.interfaces.InterfaceComponent;
import java.lang.String;

//Generated

public interface RSInterfaceComponent extends RSNode {

    String[] getActions();

    int getBorderThickness();

    int getBoundsIndex();

    int getComponentIndex();

    RSInterfaceComponent[] getComponents();

    int getContentType();

    int[][] getDynamicValues();

    int getFondId();

    int getHeight();

    int getInsetY();

    int getItemId();

    int[] getItemIds();

    int getItemStackSize();

    int[] getItemStackSizes();

    int getModelId();

    int getModelType();

    String getName();

    int getOpacity();

    int getOriginalWidth();

    int getOriginalX();

    int getOriginalY();

    RSInterfaceComponent getParent();

    int getParentUid();

    int getRelativeX();

    int getRelativeY();

    int getRotationX();

    int getRotationY();

    int getRotationZ();

    int getScrollHeight();

    int getScrollWidth();

    int getScrollX();

    String getSelectedAction();

    int getShadowColor();

    int getSpriteId();

    String[] getTableActions();

    String getText();

    int getTextColor();

    String getTooltip();

    int getType();

    int getUid();

    int getWidth();

    InterfaceComponent getWrapper();

    int getXPadding();

    int getYPadding();

    boolean isFlippedHorizontally();

    boolean isFlippedVertically();

    boolean isHidden();

    boolean isScriptAvailable();
}
