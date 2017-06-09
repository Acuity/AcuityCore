package com.acuity.api.rs.peers.mobile;

import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.peers.Renderable;
import com.acuity.rs.api.RSActor;
import com.acuity.rs.api.RSCombatInfoList;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Eclipseop.
 * Date: 6/8/2017.
 */
public abstract class Actor extends Renderable implements Locatable {

    private static final Logger logger = LoggerFactory.getLogger(Actor.class);

    private RSActor rsActor;

    public Actor(@NotNull final RSActor peer) {
        super(peer);
        Preconditions.checkNotNull(peer);
        this.rsActor = peer;
    }

    public int getSceneX() {
        return rsActor.getStrictX() >> 7;
    }

    public int getSceneY() {
        return rsActor.getStrictY() >> 7;
    }

    public int getStrictX() {
        return rsActor.getStrictX();
    }

    public int getStrictY() {
        return rsActor.getStrictY();
    }

    public int[] getPathX() {
        return rsActor.getPathX();
    }

    public int[] getPathY() {
        return rsActor.getPathY();
    }

    public int getOrientation() {
        return rsActor.getOrientation();
    }

    public int getTargetIndex() {
        return rsActor.getTargetIndex();
    }

    public int getAngle() {
        return rsActor.getAngle();
    }

    public int getAnimation() {
        return rsActor.getAnimation();
    }

    public int getQueueSize() {
        return rsActor.getQueueSize();
    }

    public int getActionAnimationDisable() {
        return rsActor.getActionAnimationDisable();
    }

    public int getActionFrame() {
        return rsActor.getActionFrame();
    }

    public RSCombatInfoList getHealthBars() {
        return rsActor.getHealthBars(); // TODO: 6/9/2017 Add wrapper
    }

    public int getIdlePoseAnimation() {
        return rsActor.getIdlePoseAnimation();
    }

    public String getOverhead() {
        return rsActor.getOverhead(); // TODO: 6/9/2017 Figure out if default state is 'null'. If it is add documentation and @Nullable
    }

    public int getPoseAnimation() {
        return rsActor.getPoseAnimation();
    }

    public int getSpellAnimationId() {
        return rsActor.getSpellAnimationId();
    }

    public int getSubAnimationFrame() {
        return rsActor.getSubAnimationFrame();
    }

    public boolean isInSequence() {
        return rsActor.isInSequence();
    }

    public int[] getHitsplatCycles() {
        return rsActor.getHitsplatCycles();
    }

    public boolean isAnimating() {
        return rsActor.getAnimation() != rsActor.getIdlePoseAnimation();
    }

    public RSActor getRsActor() {
        logger.trace("Accessing peer directly via getter.");
        return rsActor;
    }
}
