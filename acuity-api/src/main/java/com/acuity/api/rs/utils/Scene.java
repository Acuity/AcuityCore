package com.acuity.api.rs.utils;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.wrappers.scene.SceneElement;
import com.acuity.api.rs.wrappers.scene.SceneTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class Scene {

    private static final Logger logger = LoggerFactory.getLogger(Scene.class);

    public static int getPlane(){
        return AcuityInstance.getClient().getPlane();
    }

    public static byte[][][] getRenderRules(){
        return AcuityInstance.getClient().getSceneRenderRules();
    }

    public static int[][][] getTileHeights() {
        return AcuityInstance.getClient().getTileHeights();
    }

    public static int getBaseX(){
        return AcuityInstance.getClient().getBaseSceneX();
    }

    public static int getBaseY(){
        return AcuityInstance.getClient().getBaseSceneY();
    }

    public SceneElement[] getElements(){
        return AcuityInstance.getClient().getScene().getElements();
    }

    public SceneTile[][][] getTiles(){
        return AcuityInstance.getClient().getScene().getTiles();
    }
}
