package com.acuity.rs.api;

import com.acuity.api.rs.wrappers.mobile.Actor;
import java.lang.String;

//Generated

public interface RSActor extends RSRenderable {

    int getActionAnimationDisable();

    int getActionFrame();

    int getAngle();

    int getAnimation();

    RSCombatInfoList getHealthBars();

    int[] getHitsplatCycles();

    int getIdlePoseAnimation();

    int getOrientation();

    String getOverhead();

    int[] getPathX();

    int[] getPathY();

    int getPoseAnimation();

    int getQueueSize();

    int getSpellAnimationId();

    int getStrictX();

    int getStrictY();

    int getSubAnimationFrame();

    int getTargetIndex();

    Actor getWrapper();

    boolean isInSequence();
}
