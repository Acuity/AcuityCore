package com.acuity.api.rs.events.impl;

import com.acuity.api.rs.events.RSEvent;
import com.acuity.api.rs.wrappers.peers.scene.actors.Actor;

/**
 * Created by Eclipseop.
 * Date: 8/12/2017.
 */
public interface ActorAnimationChangeEvent<T extends Actor> extends RSEvent {

	int getAnimation();

	T getActor();
}
