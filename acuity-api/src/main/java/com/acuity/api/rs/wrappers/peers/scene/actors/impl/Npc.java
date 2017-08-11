package com.acuity.api.rs.wrappers.peers.scene.actors.impl;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.interfaces.Identifiable;
import com.acuity.api.rs.utils.Scene;
import com.acuity.api.rs.wrappers.common.locations.FineLocation;
import com.acuity.api.rs.wrappers.peers.scene.actors.Actor;
import com.acuity.api.rs.wrappers.peers.types.NpcType;
import com.acuity.rs.api.RSNPCType;
import com.acuity.rs.api.RSNpc;
import com.google.common.base.Preconditions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Eclipseop.
 * Date: 6/8/2017.
 */
public class Npc extends Actor implements Identifiable {

    private static final Logger logger = LoggerFactory.getLogger(Npc.class);

	private RSNpc rsNpc;

	@ClientInvoked
	public Npc(RSNpc peer) {
		super(peer);
        this.rsNpc = Preconditions.checkNotNull(peer);
	}


	@Override
	public String getName() {
	    return getType().map(NpcType::getName).orElse(null);
	}

	@Override
	public FineLocation getFineLocation(){
		Integer scale = getType().map(NpcType::getScale).orElse(0);
		return new FineLocation(getRsNpc().getFineX() - scale * 64, getRsNpc().getFineY() - scale * 64, Scene.getPlane());
	}


	@Override
	public Integer getID() {
        return getType().map(NpcType::getID).orElse(null);
	}

	@Override
	public List<String> getActions() {
        return getType().map(NpcType::getActions).map(Arrays::asList).orElse(Collections.emptyList());
	}

	public Optional<NpcType> getType() {
		return Optional.ofNullable(rsNpc.getType()).map(RSNPCType::getWrapper);
	}


    public RSNpc getRsNpc() {
        return rsNpc;
    }
}
