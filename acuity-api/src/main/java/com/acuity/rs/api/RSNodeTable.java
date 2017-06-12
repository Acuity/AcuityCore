package com.acuity.rs.api;

import com.acuity.api.rs.wrappers.structures.NodeTable;

//Generated

public interface RSNodeTable {

    RSNodeQueue getQueue();

    RSHashTable getTable();

    NodeTable getWrapper();

    RSCacheableNode invokeGet(long var0);

    void invokePut(RSCacheableNode var0, long var1);

    void invokeRemove(long var0);

    void invokeReset();
}
