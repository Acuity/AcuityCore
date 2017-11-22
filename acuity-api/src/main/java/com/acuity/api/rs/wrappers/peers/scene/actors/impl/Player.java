package com.acuity.api.rs.wrappers.peers.scene.actors.impl;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.utils.Prayers;
import com.acuity.api.rs.wrappers.peers.scene.actors.Actor;
import com.acuity.api.rs.wrappers.peers.types.PlayerType;
import com.acuity.rs.api.RSPlayer;
import com.acuity.rs.api.RSPlayerType;
import com.google.common.base.Preconditions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Eclipseop.
 * Date: 6/9/2017.
 */
public class Player extends Actor {

    private static final Logger logger = LoggerFactory.getLogger(Player.class);

    private RSPlayer rsPlayer;

    @ClientInvoked
    public Player(final RSPlayer peer) {
        super(peer);
        this.rsPlayer = Preconditions.checkNotNull(peer);
    }

    public boolean isSkulled() {
        return getSkullIcon() == 0;
    }

    public Optional<PlayerType> getAppearance() {
        return Optional.ofNullable(rsPlayer.getAppearance()).map(RSPlayerType::getWrapper);
    }

    public int getCombatLevel() {
        return rsPlayer.getCombatLevel();
    }

    public Optional<Prayers.Prayer> getPrayerByOverhead() {
        return Prayers.getPrayerByOverheadID(rsPlayer.getPrayerIcon());
    }

    //default value = -1
    public int getSkullIcon() {
        return rsPlayer.getSkullIcon();
    }

    //default value = 0
    public int getTeam() {
        return rsPlayer.getTeam();
    }

    public int getTotalLevel() {
        return rsPlayer.getTotalLevel();
    }

    public boolean isHidden() {
        return rsPlayer.isHidden();
    }

    @Override
    public List<String> getActions() {
        return Arrays.asList(rsPlayer.getActions());
    }

    @Override
    public String getName() {
        return rsPlayer.getName();
    }

    public RSPlayer getRsPlayer() {
        return rsPlayer;
    }

    public Optional<Player> getInteractingPlayer() {
        return getInteractingEntity()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity);
    }
}
