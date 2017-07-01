package com.acuity.api.rs.wrappers.peers.scene.elements.impl;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.utils.UIDs;
import com.acuity.api.rs.wrappers.common.*;
import com.acuity.api.rs.wrappers.common.SceneElement;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.rs.api.RSModel;
import com.acuity.rs.api.RSRenderable;
import com.acuity.rs.api.RSSceneBoundary;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;

import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/12/2017.
 */
public class SceneBoundary implements com.acuity.api.rs.wrappers.common.SceneElement {

    private RSSceneBoundary rsSceneBoundary;

    @ClientInvoked
    public SceneBoundary(RSSceneBoundary peer) {
        this.rsSceneBoundary = Preconditions.checkNotNull(peer);
    }

    @NotNull
    public RSSceneBoundary getRsSceneBoundary() {
        return rsSceneBoundary;
    }

    @Override
    public Optional<Model> getModel() {
        return SceneElement.getModel(
                Optional.ofNullable(rsSceneBoundary.getEntity()).orElseGet(() -> rsSceneBoundary.getRenderable2()),
                getStrictLocation(),
                null);
    }

    public UIDs.UID getUID(){
        return new UIDs.UID(rsSceneBoundary.getUid());
    }

    public int getID(){
        return getUID().getEntityID();
    }

    public StrictLocation getStrictLocation(){
        return new StrictLocation(rsSceneBoundary.getSceneX(), rsSceneBoundary.getSceneY(), rsSceneBoundary.getPlane()); // TODO: 7/1/2017 Rename
    }

    public SceneLocation getSceneLocation(){
        return getStrictLocation().getSceneLocation();
    }

    @Override
    public WorldLocation getWorldLocation() {
        return getSceneLocation().getWorldLocation();
    }
}
