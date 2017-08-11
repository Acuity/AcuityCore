package com.acuity.api.rs.wrappers.peers.structures;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.rs.api.RSCacheableNode;
import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by Zach on 6/11/2017.
 */
public class CacheableNode extends Node {

    private static final Logger logger = LoggerFactory.getLogger(CacheableNode.class);

    private RSCacheableNode rsCacheableNode;

    @ClientInvoked
    public CacheableNode(RSCacheableNode peer) {
        super(peer);
        this.rsCacheableNode = Preconditions.checkNotNull(peer);
    }

    public Optional<CacheableNode> getPrevious(){
        return Optional.ofNullable(rsCacheableNode.getPrevious()).map(RSCacheableNode::getWrapper);
    }

    public Optional<CacheableNode> getNext(){
        return Optional.ofNullable(rsCacheableNode.getNext()).map(RSCacheableNode::getWrapper);
    }


    public RSCacheableNode getRsCacheableNode() {
        return rsCacheableNode;
    }
}
