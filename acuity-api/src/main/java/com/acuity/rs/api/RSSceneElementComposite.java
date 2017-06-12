package com.acuity.rs.api;

import java.lang.String;

//Generated

public interface RSSceneElementComposite extends RSCacheableNode {

    String[] getActions();

    int getAmbient();

    int getAmbientSoundId();

    int getAnimationId();

    int getClipType();

    short[] getColors();

    int getContrast();

    int getId();

    int getMapIconId();

    int getMapSceneId();

    int getModelSizeHeight();

    int getModelSizeX();

    int getModelSizeY();

    String getName();

    short[] getNewColors();

    int[] getObjectModels();

    int[] getObjectTypes();

    int getOffsetHeight();

    int getOffsetX();

    int getOffsetY();

    int getSizeX();

    int getSizeY();

    short[] getTextureToFind();

    short[] getTextureToReplace();

    int[] getTransformIds();

    int getVarpId();

    int getVarpIndex();

    RSSceneElementComposite invokeGetImpostor();

    void invokeLoadData(RSBuffer var0, int var1);

    boolean isClipped();

    boolean isClipped1();

    boolean isNonFlatShading();

    boolean isRotated();

    boolean isSolid();
}
