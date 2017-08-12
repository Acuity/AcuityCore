package com.acuity.api.rs.events.impl;

import com.acuity.api.rs.wrappers.peers.scene.actors.impl.Npc;

/**
 * Created by Eclipseop.
 * Date: 8/12/2017.
 */
public class NpcAnimationChangeEvent implements ActorAnimationChangeEvent<Npc> {

	private Npc npc;
	private int animation;

	public NpcAnimationChangeEvent(Npc npc, int animation) {
		this.npc = npc;
		this.animation = animation;
	}

	@Override
	public int getAnimation() {
		return animation;
	}

	@Override
	public Npc getActor() {
		return npc;
	}

	@Override
	public String toString() {
		return "NpcAnimationChangeEvent{" +
				"npc=" + npc.getNullSafeName() +
				", animation=" + animation +
				'}';
	}
}
