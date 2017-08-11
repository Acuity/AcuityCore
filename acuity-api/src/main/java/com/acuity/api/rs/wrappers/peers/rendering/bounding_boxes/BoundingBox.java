package com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.wrappers.peers.structures.Node;
import com.acuity.rs.api.RSBoundingBox;


/**
 * Created by Zachary Herridge on 7/10/2017.
 */
public abstract class BoundingBox extends Node{

    private RSBoundingBox rsBoundingBox;

    @ClientInvoked
    public BoundingBox(RSBoundingBox peer) {
        super(peer);
    }


    public RSBoundingBox getRsBoundingBox() {
        return rsBoundingBox;
    }
}
