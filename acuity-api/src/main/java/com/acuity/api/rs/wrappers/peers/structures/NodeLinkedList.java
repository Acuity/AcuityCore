package com.acuity.api.rs.wrappers.peers.structures;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.rs.api.RSNode;
import com.acuity.rs.api.RSNodeLinkedList;
import com.google.common.base.Preconditions;
import com.google.common.collect.Streams;


import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Zachary Herridge on 6/12/2017.
 */
public class NodeLinkedList {

    private RSNodeLinkedList rsNodeLinkedList;

    @ClientInvoked
    public NodeLinkedList(RSNodeLinkedList peer) {
        this.rsNodeLinkedList = Preconditions.checkNotNull(peer);
    }

    public <T extends Node> Iterator<T> iterator(Class<T> tClass) {
        return new Iterator<T>() {

            private Iterator iterator = rsNodeLinkedList.iterator();

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @SuppressWarnings("unchecked")
            @Override
            public T next() {
                RSNode next = (RSNode) iterator.next();
                if (next != null && tClass.isAssignableFrom(next.getWrapper().getClass())) return (T) next.getWrapper();
                return null;
            }
        };
    }


    public RSNodeLinkedList getRsNodeLinkedList() {
        return rsNodeLinkedList;
    }
}
