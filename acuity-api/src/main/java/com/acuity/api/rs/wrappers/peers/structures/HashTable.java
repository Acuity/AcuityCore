package com.acuity.api.rs.wrappers.peers.structures;


import com.acuity.api.annotations.ClientInvoked;
import com.acuity.rs.api.RSHashTable;
import com.acuity.rs.api.RSNode;
import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Zach on 6/11/2017.
 */
public class HashTable {

    private static final Logger logger = LoggerFactory.getLogger(HashTable.class);

    private RSHashTable rsHashTable;

    @ClientInvoked
    public HashTable(RSHashTable peer) {
        this.rsHashTable = Preconditions.checkNotNull(peer);
    }

    public Node[] getBuckets(){
        return Arrays.stream(rsHashTable.getBuckets())
                .map(rsNode -> rsNode != null ? rsNode.getWrapper() : null)
                .toArray(Node[]::new);
    }

    public Optional<Node> getHead(){
        return Optional.ofNullable(rsHashTable.getHead()).map(RSNode::getWrapper);
    }

    public Optional<Node> getTail(){
        return Optional.ofNullable(rsHashTable.getTail()).map(RSNode::getWrapper);
    }

    public int getSize(){
        return rsHashTable.getSize();
    }

    public int getIndex(){
        return rsHashTable.getIndex();
    }


    public RSHashTable getRsHashTable() {
        return rsHashTable;
    }
}
