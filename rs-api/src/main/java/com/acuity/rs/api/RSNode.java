package com.acuity.rs.api;

//Generated

public interface RSNode {

    long getKey();

    RSNode getNext();

    RSNode getPrevious();

    boolean invokeLinked();

    void invokeUnlink();
}
