package com.acuity.api.rs.wrappers.structures;

import com.acuity.rs.api.RSNode;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class Node {

    private static final Logger logger = LoggerFactory.getLogger(Node.class);

    private RSNode rsNode;

    public Node(@NotNull RSNode peer) {
        this.rsNode = Preconditions.checkNotNull(peer);
    }

    public Optional<Node> getNext(){
        RSNode next = rsNode.getNext();
        return next == null ? Optional.empty() : Optional.of(new Node(next));
    }

    public Optional<Node> getPrevious(){
        RSNode previous = rsNode.getPrevious();
        return previous == null ? Optional.empty() : Optional.of(new Node(previous));
    }

    public RSNode getRsNode() {
        return rsNode;
    }
}
