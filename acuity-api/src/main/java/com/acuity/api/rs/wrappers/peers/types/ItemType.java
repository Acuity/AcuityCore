package com.acuity.api.rs.wrappers.peers.types;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.wrappers.peers.structures.CacheableNode;
import com.acuity.rs.api.RSItemType;

/**
 * Created by Zachary Herridge on 6/27/2017.
 */
public class ItemType extends CacheableNode{

    private RSItemType rsItemType;

    @ClientInvoked
    public ItemType(RSItemType rsItemType) {
        super(rsItemType);
        this.rsItemType = rsItemType;
    }

    public RSItemType getRsItemType() {
        return rsItemType;
    }
}
