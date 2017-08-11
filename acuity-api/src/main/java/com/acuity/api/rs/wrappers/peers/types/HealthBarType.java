package com.acuity.api.rs.wrappers.peers.types;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.wrappers.peers.structures.CacheableNode;
import com.acuity.rs.api.RSHealthBarType;


/**
 * Created by Zachary Herridge on 6/27/2017.
 */
public class HealthBarType extends CacheableNode{

    private RSHealthBarType rsHealthBarType;

    @ClientInvoked
    public HealthBarType(RSHealthBarType rsHealthBarType) {
        super(rsHealthBarType);
        this.rsHealthBarType = rsHealthBarType;
    }

    public int getMaxWidth(){
        return getRsHealthBarType().getMaxWidth();
    }


    public RSHealthBarType getRsHealthBarType() {
        return rsHealthBarType;
    }
}
