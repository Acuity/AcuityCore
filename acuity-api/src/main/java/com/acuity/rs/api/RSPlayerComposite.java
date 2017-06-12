package com.acuity.rs.api;


//Generated

public interface RSPlayerComposite {

    int[] getBodyPartColors();

    int[] getEquipmentIds();

    long getHash();

    int getTransformedNpcId();

    RSModel invokeGetModel(RSAnimationSequence var0, int var1, RSAnimationSequence var2, int var3);

    boolean isFemale();
}
