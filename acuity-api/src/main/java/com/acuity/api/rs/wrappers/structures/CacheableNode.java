package com.acuity.api.rs.wrappers.structures;

import com.acuity.rs.api.RSCacheableNode;
import com.acuity.rs.api.RSNode;
import com.sun.istack.internal.NotNull;

/**
 * Created by Zach on 6/11/2017.
 */
public class CacheableNode extends Node {

    public CacheableNode(@NotNull RSCacheableNode peer) {
        super(peer);
    }
}
