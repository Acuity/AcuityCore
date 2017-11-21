package com.acuity.api.rs.wrappers.peers.scene.actors;

import com.acuity.api.AcuityInstance;
import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.interfaces.Nameable;
import com.acuity.api.rs.utils.Scene;
import com.acuity.api.rs.wrappers.common.locations.FineLocation;
import com.acuity.api.rs.wrappers.common.locations.SceneLocation;
import com.acuity.api.rs.wrappers.common.locations.WorldLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenPolygon;
import com.acuity.api.rs.wrappers.peers.engine.Client;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.api.rs.wrappers.peers.rendering.Renderable;
import com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes.AxisAlignedBoundingBox;
import com.acuity.api.rs.wrappers.peers.scene.actors.accessories.HitUpdate;
import com.acuity.api.rs.wrappers.peers.scene.actors.impl.Npc;
import com.acuity.api.rs.wrappers.peers.scene.actors.impl.Player;
import com.acuity.api.rs.wrappers.peers.structures.NodeLinkedList;
import com.acuity.rs.api.RSActor;
import com.acuity.rs.api.RSHealthBar;
import com.acuity.rs.api.RSHitUpdate;
import com.acuity.rs.api.RSNodeLinkedList;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by Eclipseop.
 * Date: 6/8/2017.
 */
public abstract class Actor extends Renderable implements Locatable, Nameable {

    private static final Logger logger = LoggerFactory.getLogger(Actor.class);

    private RSActor rsActor;

    @ClientInvoked
    public Actor(final RSActor peer) {
        super(peer);
        this.rsActor = Preconditions.checkNotNull(peer);
    }

    @Override
    public Optional<Model> getCachedModel() {
        FineLocation fineLocation = getFineLocation();
        return super.getCachedModel()
                .map(model -> model.place(fineLocation.getFineX(), fineLocation.getFineY()))
                .map(model -> model.rotateTo(getOrientation()));
    }

    public int getOrientation() {
        return rsActor.getOrientation();
    }

    public int getTargetIndex() {
        return rsActor.getTargetIndex();
    }


    public int getAnimation() {
        return rsActor.getAnimation();
    }

    public Optional<NodeLinkedList> getHealthBars() {
        return Optional.ofNullable(rsActor.getHealthBars()).map(RSNodeLinkedList::getWrapper);
    }

    public int getIdlePoseAnimation() {
        return rsActor.getIdlePoseAnimation();
    }

    public int getPoseAnimation() {
        return rsActor.getPoseAnimation();
    }

    public int[] getHitsplatCycles() {
        return rsActor.getHitsplatCycles();
    }

    public boolean isAnimating() {
        return getAnimation() != getIdlePoseAnimation();
    }

    public FineLocation getFineLocation(){
        return new FineLocation(rsActor.getFineX(), rsActor.getFineY(), Scene.getPlane());
    }

    public boolean isHealthBarVisible(){
        int engineCycle = AcuityInstance.getClient().getRsClient().getEngineCycle();

        for (int i : rsActor.getHitsplatCycles()) {
            if (i > engineCycle){
                return true;
            }
        }
        return false;
    }

    public Optional<Double> getHealthPercent(){

        RSNodeLinkedList healthBars1 = rsActor.getHealthBars();

        int highestCycle = -1;
        int width = -1;

        for (Object healthBar : healthBars1) {
            if (healthBar != null && healthBar instanceof RSHealthBar){
                RSNodeLinkedList hitsplats = ((RSHealthBar) healthBar).getHitsplats();
                for (Object hitsplat : hitsplats) {
                    if (hitsplat != null && hitsplat instanceof RSHitUpdate){
                        HitUpdate hitUpdate = ((RSHitUpdate) hitsplat).getWrapper();
                        if (highestCycle <= hitUpdate.getStartCycle()){
                            highestCycle = hitUpdate.getStartCycle();
                            width = hitUpdate.getCurrentWidth();
                        }
                    }
                }
            }
        }

        if (width == -1) return Optional.empty();
        return Optional.of(Math.max(Math.min(width * 1000D / 255D, 100), 0));
    }

    public SceneLocation getSceneLocation(){
        return getFineLocation().getSceneLocation();
    }

    @Override
    public WorldLocation getWorldLocation() {
        return getSceneLocation().getWorldLocation();
    }


    public RSActor getRsActor() {
        return rsActor;
    }

    @Override
    public Supplier<ScreenPolygon> getProjectionSupplier() {
        if (!AcuityInstance.getSettings().isModelInteractionsEnabled()){
            return () -> getBoundingBox().map(AxisAlignedBoundingBox::getProjectionSupplier).map(Supplier::get).orElse(null);
        }
        return () -> getCachedModel().map(Model::getProjectionSupplier).map(Supplier::get).orElse(null);
    }

    public  Optional<Actor> getInteractingEntity() {
        try {
            int index = getTargetIndex();
            if (index == -1) return Optional.empty();

            //npc.
            final Client client = AcuityInstance.getClient();
            if (index < 32768) {
                final Npc[] npcs = AcuityInstance.getClient().getNpcs();
                final Npc npc = npcs[index];
                return npc == null ? Optional.empty() : Optional.of(npc);
            }

            //player
            index -= 32768;

            final Player[] players = client.getPlayers();
            if (index > players.length)
                return Optional.empty();

            Player player = players[index];
            return player == null ? Optional.empty() : Optional.of(player);
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }
}
