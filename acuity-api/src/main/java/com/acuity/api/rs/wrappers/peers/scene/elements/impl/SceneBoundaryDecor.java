package com.acuity.api.rs.wrappers.peers.scene.elements.impl;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.interfaces.Interactive;
import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.wrappers.common.SceneLocation;
import com.acuity.api.rs.wrappers.common.StrictLocation;
import com.acuity.api.rs.wrappers.common.WorldLocation;
import com.acuity.api.rs.utils.UIDs;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.api.rs.wrappers.peers.rendering.Renderable;
import com.acuity.api.rs.wrappers.common.SceneElement;
import com.acuity.rs.api.RSRenderable;
import com.acuity.rs.api.RSSceneBoundaryDecor;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;

import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/12/2017.
 */
public class SceneBoundaryDecor implements Locatable, Interactive, SceneElement {

    private RSSceneBoundaryDecor rsSceneBoundaryDecor;

    @ClientInvoked
    public SceneBoundaryDecor(RSSceneBoundaryDecor peer) {
        this.rsSceneBoundaryDecor = Preconditions.checkNotNull(peer);
    }

    public int getRenderFlag(){
        return rsSceneBoundaryDecor.getRenderFlag();
    }

    public int getOrientation(){
        return rsSceneBoundaryDecor.getOrientation();
    }

    public UIDs.UID getUID(){
        return new UIDs.UID(rsSceneBoundaryDecor.getUID());
    }

    public StrictLocation getStrictLocation(){
        return new StrictLocation(rsSceneBoundaryDecor.getSceneX(), rsSceneBoundaryDecor.getSceneY(), rsSceneBoundaryDecor.getPlane()); // TODO: 7/1/2017 Rename
    }

    public SceneLocation getSceneLocation(){
        return getStrictLocation().getSceneLocation();
    }

    @Override
    public WorldLocation getWorldLocation() {
        return getSceneLocation().getWorldLocation();
    }

    @NotNull
    public RSSceneBoundaryDecor getRsSceneBoundaryDecor() {
        return rsSceneBoundaryDecor;
    }

    @Override
    public Optional<Model> getModel() {
        return SceneElement.getModel(
                Optional.ofNullable(rsSceneBoundaryDecor.getEntity()).orElseGet(() -> rsSceneBoundaryDecor.getRenderable2()),
                getStrictLocation(),
                getOrientation());
    }
}
