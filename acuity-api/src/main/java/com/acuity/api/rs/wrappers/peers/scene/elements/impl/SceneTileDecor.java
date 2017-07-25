package com.acuity.api.rs.wrappers.peers.scene.elements.impl;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.utils.UIDs;
import com.acuity.api.rs.wrappers.common.SceneElement;
import com.acuity.api.rs.wrappers.common.locations.SceneLocation;
import com.acuity.api.rs.wrappers.common.locations.StrictLocation;
import com.acuity.api.rs.wrappers.common.locations.WorldLocation;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes.AxisAlignedBoundingBox;
import com.acuity.rs.api.RSAxisAlignedBoundingBox;
import com.acuity.rs.api.RSRenderable;
import com.acuity.rs.api.RSSceneTileDecor;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;

import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/12/2017.
 */
public class SceneTileDecor implements SceneElement {

    private RSSceneTileDecor rsSceneTileDecor;

    @ClientInvoked
    public SceneTileDecor(RSSceneTileDecor peer) {
        this.rsSceneTileDecor = Preconditions.checkNotNull(peer);
    }

    public StrictLocation getStrictLocation(){
        return new StrictLocation(rsSceneTileDecor.getSceneX(), rsSceneTileDecor.getSceneY(), rsSceneTileDecor.getPlane());// TODO: 7/1/2017 Rename
    }

    public SceneLocation getSceneLocation(){
        return getStrictLocation().getSceneLocation();
    }

    @Override
    public WorldLocation getWorldLocation() {
        return getSceneLocation().getWorldLocation();
    }

    public int getID(){
        return getUID().getEntityID();
    }

    @Override
    public int getFlag() {
        return 0;
    }

    public UIDs.UID getUID() {
        return new UIDs.UID(rsSceneTileDecor.getUid());
    }

    @Override
    public Optional<AxisAlignedBoundingBox> getBoundingBox() {
        return Optional.ofNullable(rsSceneTileDecor.getEntity()).map(RSRenderable::getBoundingBox).map(RSAxisAlignedBoundingBox::getWrapper);
    }

    @Override
    public Optional<Model> getModel() {
        return com.acuity.api.rs.wrappers.common.SceneElement.getModel(
                rsSceneTileDecor.getEntity(),
                getStrictLocation(),
                null);
    }

    @Override
    public int getOrientation() {
        return 0;
    }

    @NotNull
    public RSSceneTileDecor getRsSceneTileDecor() {
        return rsSceneTileDecor;
    }
}
