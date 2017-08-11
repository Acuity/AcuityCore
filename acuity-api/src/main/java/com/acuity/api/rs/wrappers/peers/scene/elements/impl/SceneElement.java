package com.acuity.api.rs.wrappers.peers.scene.elements.impl;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.interfaces.Interactive;
import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.utils.UIDs;
import com.acuity.api.rs.wrappers.common.locations.FineLocation;
import com.acuity.api.rs.wrappers.common.locations.SceneLocation;
import com.acuity.api.rs.wrappers.common.locations.WorldLocation;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes.AxisAlignedBoundingBox;
import com.acuity.rs.api.RSAxisAlignedBoundingBox;
import com.acuity.rs.api.RSRenderable;
import com.acuity.rs.api.RSSceneElement;
import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class SceneElement implements Locatable, Interactive, com.acuity.api.rs.wrappers.common.SceneElement {

    private static final Logger logger = LoggerFactory.getLogger(SceneElement.class);

    private RSSceneElement rsSceneElement;

    @ClientInvoked
    public SceneElement(RSSceneElement peer) {
        this.rsSceneElement = Preconditions.checkNotNull(peer);
    }

    @Override
    public Optional<AxisAlignedBoundingBox> getBoundingBox() {
        return Optional.ofNullable(rsSceneElement.getEntity()).map(RSRenderable::getBoundingBox).map(RSAxisAlignedBoundingBox::getWrapper);
    }

    public Optional<Model> getModel() {
        return com.acuity.api.rs.wrappers.common.SceneElement.getModel(
                rsSceneElement.getEntity(),
                getFineLocation(),
                getOrientation());
    }

    public int getFlag() {
        //// TODO: 7/25/2017 Add flag hook
        return 0;
    }

    public int getHeight() {
        return rsSceneElement.getHeight();
    }

    @Override
    public int getOrientation() {
        return rsSceneElement.getOrientation();
    }

    public FineLocation getFineLocation(){
        return new FineLocation(rsSceneElement.getEndSceneX(), rsSceneElement.getEndSceneY(), rsSceneElement.getPlane());
    }

    public SceneLocation getSceneLocation() {
        return getFineLocation().getSceneLocation();
    }

    @Override
    public WorldLocation getWorldLocation() {
        return getSceneLocation().getWorldLocation();
    }

    public int getID() {
        return getUID().getEntityID();
    }

    public UIDs.UID getUID() {
        return new UIDs.UID(rsSceneElement.getUID());
    }


    public RSSceneElement getRsSceneElement() {
        return rsSceneElement;
    }
}
