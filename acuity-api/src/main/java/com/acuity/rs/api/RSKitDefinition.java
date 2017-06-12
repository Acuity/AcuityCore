package com.acuity.rs.api;


//Generated

public interface RSKitDefinition extends RSCacheableNode {

    int getBodyPartId();

    int[] getModelIds();

    int[] getModels();

    short[] getRecolorToFind();

    short[] getRecolorToReplace();

    short[] getRetextureToFind();

    short[] getRetextureToReplace();

    RSModelComposite invokeGetModelData();

    boolean invokeReady();

    boolean isNonSelectable();
}
