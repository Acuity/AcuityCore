package com.acuity.rs.api;

import com.acuity.api.rs.wrappers.structures.Node;

//Generated

public interface RSNode {

    long getKey();

    RSNode getNext();

    RSNode getPrevious();

    Node getWrapper();

    boolean invokeLinked();

    void invokeUnlink();
}
