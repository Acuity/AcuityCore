package com.acuity.api.rs.wrappers.scene;


import com.acuity.rs.api.RSScene;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class Scene {

    private static final Logger logger = LoggerFactory.getLogger(Scene.class);

    private RSScene rsScene;

    public Scene(RSScene peer) {
        this.rsScene = Preconditions.checkNotNull(peer);
    }

    public SceneElement[] getElements(){
        logger.trace("Wrapping RSSceneElement[] from RSScene.");
        return Arrays.stream(rsScene.getElements())
                .map(peer -> peer != null ? new SceneElement(peer) : null)
                .toArray(SceneElement[]::new);
    }

    public SceneTile[][][] getTiles(){
        logger.trace("Wrapping RSSceneTile[][][] from RSScene.");
        return Arrays.stream(rsScene.getTiles())
                .map(tiles1 -> Arrays.stream(tiles1)
                        .map(tiles2 -> Arrays.stream(tiles2)
                                .map(peer -> peer != null ? new SceneTile(peer) : null)
                                .toArray(SceneTile[]::new)
                        ).toArray(SceneTile[][]::new)
                ).toArray(SceneTile[][][]::new);
    }

    public RSScene getRsScene() {
        return rsScene;
    }
}
