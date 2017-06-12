package com.acuity.api.rs.utils;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.movement.SceneLocation;
import com.acuity.api.rs.wrappers.engine.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Zachary Herridge on 6/9/2017.
 */
public class Perspective {

    private static final Logger logger = LoggerFactory.getLogger(Perspective.class);

    public static final int[] SINE = new int[2048];
    public static final int[] COSINE = new int[2048];
    private static final double UNIT = Math.PI / 1024d;
    private static final int LOCAL_COORD_BITS = 7;
    public static final int LOCAL_TILE_SIZE = 1 << LOCAL_COORD_BITS;

    static {
        for (int i = 0; i < 2048; ++i) {
            SINE[i] = (int) (65536.0D * Math.sin((double) i * UNIT));
            COSINE[i] = (int) (65536.0D * Math.cos((double) i * UNIT));
        }
    }

    public static Point worldToCanvas(int x, int y, int plane) {
        return worldToCanvas(x, y, plane, 0);
    }

    public static Point worldToCanvas(int x, int y, int plane, int zOffset) {
        if (x >= 128 && y >= 128 && x <= 13056 && y <= 13056) {
            int z = getTileHeight(x, y, Scene.getPlane()) - plane;
            x -= Camera.getX();
            y -= Camera.getY();
            z -= Camera.getZ();
            z -= zOffset;

            int cameraPitch = Camera.getPitch();
            int cameraYaw = Camera.getYaw();

            int pitchSin = SINE[cameraPitch];
            int pitchCos = COSINE[cameraPitch];
            int yawSin = SINE[cameraYaw];
            int yawCos = COSINE[cameraYaw];

            int var8 = yawCos * x + y * yawSin >> 16;
            y = yawCos * y - yawSin * x >> 16;
            x = var8;
            var8 = pitchCos * z - y * pitchSin >> 16;
            y = z * pitchSin + y * pitchCos >> 16;

            if (y >= 50) {
                Client client = AcuityInstance.getClient();
                int pointX = client.getViewportHeight()  / 2 + x * client.getViewportScale() / y;
                int pointY = var8 * client.getViewportScale() / y + client.getViewportWidth() / 2;
                return new Point(pointX, pointY);
            }
        }

        return null;
    }

    public static Point worldToMiniMap(int x, int y, int distance)
    {
        int angle = MiniMap.getScale() + MiniMap.getRotation() & 0x7FF;

        SceneLocation sceneLocation = LocalPlayer.getSceneLocation();
        x = x / 32 - sceneLocation.getSceneX() / 32;
        y = y / 32 - sceneLocation.getSceneY() / 32;

        int dist = x * x + y * y;
        if (dist < distance)
        {
            int sin = SINE[angle];
            int cos = COSINE[angle];

            sin = sin * 256 / (MiniMap.getOffset() + 256);
            cos = cos * 256 / (MiniMap.getOffset() + 256);

            int xx = y * sin + cos * x >> 16;
            int yy = sin * x - y * cos >> 16;

            Client client = AcuityInstance.getClient();
            int miniMapX = client.getViewportWidth() - (!client.isResized() ? 208 : 167);

            x = (miniMapX + 167 / 2) + xx;
            y = (167 / 2 - 1) + yy;
            return new Point(x, y);
        }

        return new Point(-1, -1);
    }

    public static int getTileHeight(int x, int y, int plane) {
        int var3 = x >> 7;
        int var4 = y >> 7;
        if (var3 >= 0 && var4 >= 0 && var3 <= 103 && var4 <= 103) {
            byte[][][] tileSettings = Scene.getRenderRules();
            int[][][] tileHeights = Scene.getTileHeights();

            int var5 = plane;
            if (plane < 3 && (tileSettings[1][var3][var4] & 2) == 2) {
                var5 = plane + 1;
            }

            int var6 = x & 127;
            int var7 = y & 127;
            int var8 = var6 * tileHeights[var5][var3 + 1][var4] + (128 - var6) * tileHeights[var5][var3][var4] >> 7;
            int var9 = tileHeights[var5][var3][var4 + 1] * (128 - var6) + var6 * tileHeights[var5][var3 + 1][var4 + 1] >> 7;
            return (128 - var7) * var8 + var7 * var9 >> 7;
        }

        return 0;
    }

    public static Point getCanvasTextLocation(Graphics2D graphics, Point localLocation, String text, int zOffset) {
        int plane = Scene.getPlane();

        Point p = worldToCanvas((int) localLocation.getX(), (int) localLocation.getY(), plane, zOffset);
        if (p == null) {
            return null;
        }

        FontMetrics fm = graphics.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, graphics);
        int xOffset = (int) (p.getX() - (int) (bounds.getWidth() / 2));

        return new Point(xOffset, (int) p.getY());
    }
}
