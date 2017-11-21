package com.acuity.api.rs.utils;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.wrappers.common.locations.FineLocation;
import com.acuity.api.rs.wrappers.common.locations.SceneLocation;
import com.acuity.api.rs.wrappers.common.locations.WorldLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation3D;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenPolygon;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenRectangle;
import com.acuity.api.rs.wrappers.peers.engine.Client;
import com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes.AxisAlignedBoundingBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Optional;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class Projection {

    private static final Logger logger = LoggerFactory.getLogger(Projection.class);
    public static final ScreenPolygon GAME_SCREEN = new ScreenRectangle(new ScreenLocation(4, 4), new ScreenLocation(512, 334));
    public static final Dimension APPLET_SIZE = new Dimension(765, 503);

    public static final int TILE_PIXEL_SIZE = 128;
    public static final int[] SINE = new int[2048];
    public static final int[] COSINE = new int[2048];

    private static final int[][] AABB_SIDES = {
            {0, 1, 2, 3},
            {4, 5, 6, 7},

            {0, 4, 5, 1},
            {2, 6, 7, 3},

            {1, 5, 6, 2},
            {3, 7, 4, 0}
    };

    private static final int[][] AABB_TRIANGLES = {
            {0, 1, 3},
            {2, 3, 1},
    };

    static {
        for (int i = 0; i < SINE.length; i++) {
            SINE[i] = (int) (65536.0D * Math.sin((double) i * 0.0030679615D));
            COSINE[i] = (int) (65536.0D * Math.cos((double) i * 0.0030679615D));
        }
    }

    public static Optional<ScreenLocation> worldToScreen(WorldLocation worldLocation) {
        return sceneToScreen(worldLocation.toCurrentSceneLocation());
    }

    public static Optional<ScreenLocation> sceneToScreen(SceneLocation sceneLocation) {
        return fineToScreen(sceneLocation.getFineLocation());
    }

    public static Optional<ScreenLocation> fineToScreen(FineLocation fineLocation) {
        return fineToScreen(fineLocation.getFineX(), fineLocation.getFineY(), fineLocation.getPlane());
    }

    public static Optional<ScreenLocation> fineToScreen(int fineX, int fineY, int height) {
        if (fineX >= TILE_PIXEL_SIZE && fineX <= 13056 && fineY >= TILE_PIXEL_SIZE && fineY <= 13056) {
            int alt = Camera.getPitch();
            if (alt < 0) {
                return Optional.empty();
            }
            int yaw = Camera.getYaw();
            if (yaw < 0) {
                return Optional.empty();
            }
            int elevation = Scene.getGroundHeight(fineX, fineY).orElse(0) - height;
            fineX -= Camera.getX();
            fineY -= Camera.getY();
            elevation -= Camera.getZ();
            int altSin = SINE[alt];
            int altCos = COSINE[alt];
            int yawSin = SINE[yaw];
            int yawCos = COSINE[yaw];
            int angle = fineY * yawSin + fineX * yawCos >> 16;
            fineY = fineY * yawCos - fineX * yawSin >> 16;
            fineX = angle;
            angle = elevation * altCos - fineY * altSin >> 16;
            fineY = elevation * altSin + fineY * altCos >> 16;
            if (fineY == 0) {
                return Optional.empty();
            }

   /*         fineX = fineX * AcuityInstance.getClient().getViewportScale() / fineY + AcuityInstance.getClient().getViewportWidth() / 2;
            fineY = elevation * AcuityInstance.getClient().getViewportScale() / fineY + AcuityInstance.getClient().getViewportHeight() / 2;
*/
            return Optional.of(new ScreenLocation(256 + (fineX << 9) / fineY, (angle << 9) / fineY + 167));
        }
        return Optional.empty();
    }

    public static Optional<ScreenLocation> sceneToMiniMap(int sceneX, int sceneY) {
        return sceneToMiniMap(sceneX, sceneY, null);
    }

    public static Optional<ScreenLocation> sceneToMiniMap(int sceneX, int sceneY, Integer distanceFilter) {
        int angle = MiniMap.getRotation() & 0x7FF;

        SceneLocation sceneLocation = LocalPlayer.getSceneLocation().orElseThrow(() -> new NullPointerException("LocalPlayer.getSceneLocation() failed to return a location."));
        sceneX = sceneX / 32 - sceneLocation.getSceneX() / 32;
        sceneY = sceneY / 32 - sceneLocation.getSceneY() / 32;

        int dist = sceneX * sceneX + sceneY * sceneY;
        if (distanceFilter == null || dist < distanceFilter) {
            int sin = SINE[angle];
            int cos = COSINE[angle];

            int xx = sceneY * sin + cos * sceneX >> 16;
            int yy = sin * sceneX - sceneY * cos >> 16;

            Client client = AcuityInstance.getClient();
            int miniMapX = client.getViewportWidth() - (!client.isResized() ? 208 : 167);

            sceneX = (miniMapX + 167 / 2) + xx;
            sceneY = (167 / 2 - 1) + yy;
            return Optional.of(new ScreenLocation(sceneX, sceneY));
        }

        return Optional.empty();
    }

    /**
     * @author Dogerina
     */
    public static int[][][] boundingBoxToScreen(AxisAlignedBoundingBox boundingBox){
        ScreenLocation3D[] vertices = boundingBox.getVertices();
        int[][][] model = new int[AABB_SIDES.length * AABB_TRIANGLES.length][3][3];
        for (int[] side : AABB_SIDES) {
            for (int face = 0; face < AABB_TRIANGLES.length; face++) {
                int[] triangle = AABB_TRIANGLES[face];
                model[face][0] = vertices[side[triangle[0]]].toArray();
                model[face][1] = vertices[side[triangle[1]]].toArray();
                model[face][2] = vertices[side[triangle[2]]].toArray();
            }
        }
        return model;
    }
}
