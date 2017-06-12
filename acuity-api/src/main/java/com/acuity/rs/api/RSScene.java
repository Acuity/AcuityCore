package com.acuity.rs.api;

import com.acuity.api.rs.wrappers.scene.Scene;

//Generated

public interface RSScene {

    RSSceneElement[] getElements();

    RSSceneTile[][][] getTiles();

    Scene getWrapper();

    void invokeGroundObjectSpawned(int var0, int var1, int var2, int var3, RSRenderable var4, int var5, int var6);
}
