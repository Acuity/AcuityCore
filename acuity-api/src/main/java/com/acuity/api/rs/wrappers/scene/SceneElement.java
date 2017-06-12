package com.acuity.api.rs.wrappers.scene;

import com.acuity.rs.api.RSSceneElement;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class SceneElement {

    private static final Logger logger = LoggerFactory.getLogger(SceneElement.class);

    private RSSceneElement rsSceneElement;

    public SceneElement(RSSceneElement peer) {
        this.rsSceneElement = Preconditions.checkNotNull(peer);
    }

    public RSSceneElement getRsSceneElement() {
        return rsSceneElement;
    }
}
