package com.acuity.rs.api;


//Generated

public interface RSAnimationSequence extends RSCacheableNode {

    int getForcedPriority();

    int[] getFrameIDs();

    int[] getFrameLenghts();

    int getFrameStep();

    int[] getInterleaveLeave();

    int getLeftHandItem();

    int getMaxLoops();

    int getPrecedenceAnimating();

    int getPriority();

    int getReplyMode();

    int getRightHandItem();

    boolean isStretches();
}
