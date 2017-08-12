package com.acuity.api.rs.events.impl;

import com.acuity.rs.api.RSActor;

/**
 * Created by Eclipseop.
 * Date: 8/12/2017.
 */
public class ActorAnimationChangeEvent {

	private RSActor actor;
	private int animation;

	public ActorAnimationChangeEvent(RSActor actor, int animation) {
		this.actor = actor;
		this.animation = animation;
	}

	public int getAnimation() {
		return animation;
	}

	public RSActor getActor() {
		return actor;
	}

	@Override
	public String toString() {
		return "ActorAnimationChangeEvent{" +
				"actor=" + actor +
				", animation=" + animation +
				'}';
	}
}
