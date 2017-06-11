package com.acuity.rs.api;

//Generated

public interface RSItemComposite extends RSCacheableNode {

    int getAmbient();

    short[] getColourToReplace();

    short[] getColourToReplaceWith();

    int getContrast();

    int[] getCountCo();

    int[] getCountObj();

    int getFemaleHeadModel();

    int getFemaleHeadModel2();

    int getFemaleModel();

    int getFemaleModel1();

    int getFemaleModel2();

    int getFemaleOffset();

    String[] getGroundActions();

    int getId();

    String[] getInventoryActions();

    int getInventoryModel();

    int getIsStackable();

    int getMaleHeadModel();

    int getMaleHeadModel2();

    int getMaleModel();

    int getMaleModel1();

    int getMaleModel2();

    int getMaleOffset();

    String getName();

    int getNote();

    int getNotedTemplate();

    int getOffsetX2d();

    int getOffsetY2d();

    int getPrice();

    int getResizeX();

    int getResizeY();

    int getResizeZ();

    int getTeam();

    short[] getTextToReplaceWith();

    short[] getTextureToReplace();

    int getXan2d();

    int getYan2d();

    int getZan2d();

    int getZoom2d();

    boolean invokeGetEquipmentModel(boolean var0);

    RSModel invokeGetModel(int var0);

    RSModelComposite invokeGetWornModelData(boolean var0);

    void invokeLoadBuffer(RSBuffer var0);

    void invokePopulateFromBuffer(RSBuffer var0, int var1);

    boolean isMembers();
}
