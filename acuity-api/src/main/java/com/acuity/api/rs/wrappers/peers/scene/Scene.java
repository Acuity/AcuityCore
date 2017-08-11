package com.acuity.api.rs.wrappers.peers.scene;


import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.wrappers.peers.scene.elements.impl.SceneElement;
import com.acuity.rs.api.RSScene;
import com.acuity.rs.api.RSSceneTile;
import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class Scene {

    private static final Logger logger = LoggerFactory.getLogger(Scene.class);

    private RSScene rsScene;

    @ClientInvoked
    public Scene(RSScene peer) {
        this.rsScene = Preconditions.checkNotNull(peer);
    }

    public SceneElement[] getElements(){// TODO: 6/12/2017 Can this be null? Also what is this?
        logger.trace("Wrapping RSSceneElement[] from RSScene.");
        return Arrays.stream(rsScene.getElements())
                .map(peer -> peer != null ? new SceneElement(peer) : null)
                .toArray(SceneElement[]::new);
    }

    public Optional<SceneTile> getTile(int x, int y, int plane){
        return Optional.ofNullable(rsScene.getTiles()).map(rsSceneTiles -> rsSceneTiles[plane][x][y]).map(RSSceneTile::getWrapper);
    }


    public RSScene getRsScene() {
        return rsScene;
    }
}
