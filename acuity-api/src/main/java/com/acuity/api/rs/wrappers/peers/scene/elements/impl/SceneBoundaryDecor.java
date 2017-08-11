package com.acuity.api.rs.wrappers.peers.scene.elements.impl;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.interfaces.Interactive;
import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.utils.UIDs;
import com.acuity.api.rs.wrappers.common.SceneElement;
import com.acuity.api.rs.wrappers.common.locations.FineLocation;
import com.acuity.api.rs.wrappers.common.locations.SceneLocation;
import com.acuity.api.rs.wrappers.common.locations.WorldLocation;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes.AxisAlignedBoundingBox;
import com.acuity.rs.api.RSAxisAlignedBoundingBox;
import com.acuity.rs.api.RSRenderable;
import com.acuity.rs.api.RSSceneBoundaryDecor;
import com.google.common.base.Preconditions;


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

    @Override
    public int getOrientation(){
        return rsSceneBoundaryDecor.getOrientation();
    }

    public UIDs.UID getUID(){
        return new UIDs.UID(rsSceneBoundaryDecor.getUID());
    }

    public FineLocation getFineLocation(){
        return new FineLocation(rsSceneBoundaryDecor.getSceneX(), rsSceneBoundaryDecor.getSceneY(), rsSceneBoundaryDecor.getPlane());
    }

    public SceneLocation getSceneLocation(){
        return getFineLocation().getSceneLocation();
    }

    @Override
    public WorldLocation getWorldLocation() {
        return getSceneLocation().getWorldLocation();
    }


    public RSSceneBoundaryDecor getRsSceneBoundaryDecor() {
        return rsSceneBoundaryDecor;
    }

    @Override
    public int getID() {
        return getUID().getEntityID();
    }

    @Override
    public int getFlag() {
        return 0;
    }

    @Override
    public Optional<AxisAlignedBoundingBox> getBoundingBox() {
        return getRenderable().map(RSRenderable::getBoundingBox).map(RSAxisAlignedBoundingBox::getWrapper);
    }

    @Override
    public Optional<Model> getModel() {
        return SceneElement.getModel(
                getRenderable().orElse(null),
                getFineLocation(),
                getOrientation());
    }

    private Optional<RSRenderable> getRenderable(){
        RSRenderable entity = rsSceneBoundaryDecor.getEntity();
        return Optional.ofNullable(entity != null ? entity : rsSceneBoundaryDecor.getLinkedEntity());
    }
}
