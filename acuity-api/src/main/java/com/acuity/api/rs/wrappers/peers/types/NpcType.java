package com.acuity.api.rs.wrappers.peers.types;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.wrappers.peers.structures.CacheableNode;
import com.acuity.rs.api.RSNPCType;


/**
 * Created by Zachary Herridge on 6/27/2017.
 */
public class NpcType extends CacheableNode {

    private RSNPCType rsNpcType;

    @ClientInvoked
    public NpcType(RSNPCType rsNpcType) {
        super(rsNpcType);
        this.rsNpcType = rsNpcType;
    }

    public int getScale(){
        return rsNpcType.getScaleXY();
    }

    public String getName() {
        return rsNpcType.getName();
    }

    public int getID(){
        return rsNpcType.getID();
    }

    public String[] getActions(){
        return rsNpcType.getActions();
    }


    public RSNPCType getRsNpcComposite() {
        return rsNpcType;
    }
}
