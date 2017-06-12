package com.acuity.rs.api;


//Generated

public interface RSModelComposite extends RSRenderable {

    byte[] getFaceAlphas();

    short[] getFaceColor();

    RSNormalFace[] getFaceNormals();

    byte[] getFaceRenderPriorities();

    byte[] getFaceRenderType();

    short[] getFaceTextures();

    RSNormalVertex[] getNormals();

    byte getPriority();

    short[] getTexTriangleX();

    short[] getTexTriangleY();

    short[] getTexTriangleZ();

    byte[] getTextureCoords();

    short[] getTexturePrimaryColor();

    byte[] getTextureRenderTypes();

    int getTriangleFaceCount();

    int[] getTrianglePointsX();

    int[] getTrianglePointsY();

    int[] getTrianglePointsZ();

    int[] getTriangleSkinValues();

    int getVertexCount();

    int[] getVertexSkins();

    int[] getVertexX();

    int[] getVertexY();

    int[] getVertexZ();

    void invokeComputeAnimationTables();

    void invokeComputeNormals();

    RSModel invokeLight(int var0, int var1, int var2, int var3, int var4);
}
