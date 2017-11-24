package com.acuity.api.rs.wrappers.common.locations;

import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.utils.Projection;
import com.acuity.api.rs.utils.Scene;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

import java.util.Optional;

/**
 * Created by Zach on 7/1/2017.
 */
public class FineLocation implements Locatable {

    private int fineX, fineY, plane;
    private int baseX, baseY;

    public FineLocation(int fineX, int fineY, int plane) {
        this(fineX, fineY, plane, Scene.getBaseX(), Scene.getBaseY());
    }

    public FineLocation(int fineX, int fineY, int plane, int baseX, int baseY) {
        this.fineX = fineX;
        this.fineY = fineY;
        this.plane = plane;
        this.baseX = baseX;
        this.baseY = baseY;
    }

    public int getPlane() {
        return plane;
    }

    public int getFineX() {
        return fineX;
    }

    public int getFineY() {
        return fineY;
    }

    public SceneLocation getSceneLocation(){
        return new SceneLocation(fineX / Projection.TILE_PIXEL_SIZE, fineY / Projection.TILE_PIXEL_SIZE, plane, baseX, baseY);
    }

    @Override
    public WorldLocation getWorldLocation(){
        return getSceneLocation().getWorldLocation();
    }

    @Override
    public Optional<ScreenLocation> getScreenLocation() {
        return Projection.fineToScreen(this);
    }
}
