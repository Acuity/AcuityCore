package com.acuity.api.rs.events.impl;

import com.acuity.api.rs.wrappers.peers.scene.actors.impl.Player;

/**
 * Created by MadDev on 7/27/17.
 */
public class PlayerAnimationChangeEvent implements ActorAnimationChangeEvent<Player> {

    private Player player;
    private int animation;

    public PlayerAnimationChangeEvent(Player player, int animation) {
        this.player = player;
        this.animation = animation;
    }

    @Override
    public Player getActor() {
        return player;
    }

    @Override
    public int getAnimation() {
        return animation;
    }

    @Override
    public String toString() {
        return "PlayerAnimationChangeEvent{" +
                "player=" + player.getNullSafeName() +
                ", animation=" + animation +
                '}';
    }
}
