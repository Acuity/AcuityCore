package com.acuity.api.rs.wrappers.peers.types;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.wrappers.peers.structures.CacheableNode;
import com.acuity.rs.api.RSSceneElementType;


/**
 * Created by Zachary Herridge on 6/27/2017.
 */
public class SceneElementType extends CacheableNode{

    private RSSceneElementType rsSceneElementType;

    @ClientInvoked
    public SceneElementType(RSSceneElementType rsSceneElementType) {
        super(rsSceneElementType);
        this.rsSceneElementType = rsSceneElementType;
    }

    public String[] getActions(){
        return rsSceneElementType.getActions();
    }

    public String getName(){
        return rsSceneElementType.getName();
    }


    public RSSceneElementType getRsSceneElementType() {
        return rsSceneElementType;
    }
}
