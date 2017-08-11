package com.acuity.api.rs.wrappers.peers.rendering;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.interfaces.Interactive;
import com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes.AxisAlignedBoundingBox;
import com.acuity.api.rs.wrappers.peers.structures.CacheableNode;
import com.acuity.rs.api.RSAxisAlignedBoundingBox;
import com.acuity.rs.api.RSRenderable;
import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by Eclipseop.
 * Date: 6/8/2017.
 */
public abstract class Renderable extends CacheableNode implements Interactive {

    private static final Logger logger = LoggerFactory.getLogger(Renderable.class);

    protected RSRenderable rsRenderable;

    @ClientInvoked
    public Renderable(final RSRenderable peer) {
        super(peer);
        this.rsRenderable = Preconditions.checkNotNull(peer);
    }

    public int getHeight() {
        return rsRenderable.getHeight();
    }

    public Optional<Model> getCachedModel() {
        return Optional.ofNullable(rsRenderable.getCachedModel());
    }

    public Optional<AxisAlignedBoundingBox> getBoundingBox(){
        return Optional.ofNullable(rsRenderable.getBoundingBox()).map(RSAxisAlignedBoundingBox::getWrapper);
    }


    public RSRenderable getRsRenderable() {
        return rsRenderable;
    }
}
