package com.acuity.api.rs.wrappers.structures;

import com.acuity.rs.api.RSHashTable;
import com.acuity.rs.api.RSNode;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by Zach on 6/11/2017.
 */
public class NodeTable<T extends Node> {

    private static final Logger logger = LoggerFactory.getLogger(NodeTable.class);

    private RSHashTable rsHashTable;

    public NodeTable(RSHashTable peer) {
        this.rsHashTable = Preconditions.checkNotNull(peer);
    }
    @SuppressWarnings("unchecked")
    public <T> T[] getBuckets(T[] a) {
        RSNode[] buckets = rsHashTable.getBuckets();
        int size = buckets.length;
        if (a.length < size) return (T[]) Arrays.copyOf(buckets, size, a.getClass());
        System.arraycopy(buckets, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }


    public RSHashTable getRsHashTable() {
        return rsHashTable;
    }
}
