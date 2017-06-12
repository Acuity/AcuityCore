package com.acuity.api.rs.wrappers.engine;

import com.acuity.api.rs.wrappers.interfaces.InterfaceComponent;
import com.acuity.api.rs.wrappers.mobile.Npc;
import com.acuity.api.rs.wrappers.mobile.Player;
import com.acuity.api.rs.wrappers.scene.Scene;
import com.acuity.api.rs.wrappers.structures.Node;
import com.acuity.api.rs.wrappers.structures.NodeTable;
import com.acuity.rs.api.RSClient;
import com.acuity.rs.api.RSPlayer;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class Client extends GameEngine {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    private final RSClient rsClient;

    public Client(@NotNull RSClient peer) {
        super(peer);
        this.rsClient = Preconditions.checkNotNull(peer);
    }

    //init in loading screen, could technically be null before then
    public Scene getScene(){
        return new Scene(rsClient.getSceneGraph());
    }

    public Player[] getPlayers(){
        logger.trace("Wrapping RSPlayer[] from RSClient.");
        return Arrays.stream(rsClient.getPlayers())
                .map(peer -> peer != null ? new Player(peer) : null)
                .toArray(Player[]::new);
    }

    public Npc[] getNpcs(){
        logger.trace("Wrapping RSNpc[] from RSClient.");
        return Arrays.stream(rsClient.getNpcs())
                .map(peer -> peer != null ? new Npc(peer) : null)
                .toArray(Npc[]::new);
    }

    public Optional<Player> getLocalPlayer(){
        logger.trace("Wrapping RSPlayer-local from RSClient.");
        final RSPlayer localPlayer = rsClient.getLocalPlayer();
        if (localPlayer == null) return Optional.empty();
        return Optional.of(new Player(localPlayer));
    }


    public InterfaceComponent[][] getInterfaces() {
        logger.trace("Wrapping RSInterfaceComponent[][] from RSClient.");
        return Arrays.stream(rsClient.getInterfaces())
                .map(rsInterfaceComponents -> Arrays.stream(rsInterfaceComponents)
                        .map(peer -> peer != null ? new InterfaceComponent(peer) : null)
                        .toArray(InterfaceComponent[]::new)
                ).toArray(InterfaceComponent[][]::new);
    }

    public int getPlane() {
        return rsClient.getPlane();
    }

    public int getBaseSceneX() {
        return rsClient.getBaseScenceX();
    }

    public int getBaseSceneY() {
        return rsClient.getBaseSceneY();
    }

    public int getGameState() {
        return rsClient.getGameState();
    }

    public int[][][] getTileHeights() {
        return rsClient.getTileHeights();
    }

    public byte[][][] getSceneRenderRules() {
        return rsClient.getSceneRenderRules();
    }

    public int getCameraX() {
        return rsClient.getCameraX();
    }

    public int getCameraY() {
        return rsClient.getCameraY();
    }

    public int getCameraZ() {
        return rsClient.getCameraZ();
    }

    public int getCameraPitch() {
        return rsClient.getCameraPitch();
    }

    public int getCameraYaw() {
        return rsClient.getCameraYaw();
    }

    public int getViewportHeight() {
        return rsClient.getViewportHeight();
    }

    public int getViewportScale() {
        return rsClient.getViewportScale();
    }

    public int getViewportWidth() {
        return rsClient.getViewportWidth();
    }

    public int getMapScale() {
        return rsClient.getMapScale();
    }

    public int getMapRotation() {
        return rsClient.getMapRotation();
    }

    public int getMinimapOffset() {
        return rsClient.getMinimapOffset();
    }

    public boolean isResized() {
        return rsClient.isResized();
    }

    public NodeTable<Node> getInterfaceNodeTable() {
        return new NodeTable<>(rsClient.getInterfaceNodes());
    }

    public RSClient getRsClient(){
        return rsClient;
    }
}
