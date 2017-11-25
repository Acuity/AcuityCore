package com.acuity.api.rs.wrappers.peers.scene.elements.impl;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.utils.UIDs;
import com.acuity.api.rs.wrappers.common.SceneElement;
import com.acuity.api.rs.wrappers.common.locations.FineLocation;
import com.acuity.api.rs.wrappers.common.locations.SceneLocation;
import com.acuity.api.rs.wrappers.common.locations.WorldLocation;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes.AxisAlignedBoundingBox;
import com.acuity.rs.api.RSAxisAlignedBoundingBox;
import com.acuity.rs.api.RSRenderable;
import com.acuity.rs.api.RSSceneTileDecor;
import com.google.common.base.Preconditions;


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

    public FineLocation getFineLocation(){
        return new FineLocation(rsSceneTileDecor.getSceneX(), rsSceneTileDecor.getSceneY(), rsSceneTileDecor.getPlane());
    }

    public SceneLocation getSceneLocation(){
        return getFineLocation().getSceneLocation();
    }

    @Override
    public WorldLocation getWorldLocation() {
        return getSceneLocation().getWorldLocation();
    }

    @Override
    public int getFlag() {
        return 0;
    }

    @Override
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
                getFineLocation(),
                null);
    }

    @Override
    public int getOrientation() {
        return 0;
    }


    public RSSceneTileDecor getRsSceneTileDecor() {
        return rsSceneTileDecor;
    }
}
