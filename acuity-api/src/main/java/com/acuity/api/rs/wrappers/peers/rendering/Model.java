package com.acuity.api.rs.wrappers.peers.rendering;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.utils.Projection;
import com.acuity.rs.api.RSModel;
import com.google.common.base.Preconditions;
import com.sun.istack.internal.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by Zachary Herridge on 6/12/2017.
 */
public class Model extends Renderable {

    private RSModel rsModel;

    private int strictX, strictY;

    private boolean inited = false;
    private int[] xVertices, yVertices, zVertices;
    private int[] xTriangles, yTriangles, zTriangles;
    private int[] initialXVertices, initialZVertices;

    @ClientInvoked
    public Model(RSModel peer) {
        super(peer);
        this.rsModel = Preconditions.checkNotNull(peer);

        if (peer.getXTriangles() != null && peer.getXVertices() != null){
            int count = peer.getXVertices().length;
            xTriangles = Arrays.copyOf(peer.getXTriangles(), count);
            yTriangles = Arrays.copyOf(peer.getYTriangles(), count);
            zTriangles = Arrays.copyOf(peer.getZTriangles(), count);
            count = peer.getXTriangles().length;
            xVertices = Arrays.copyOf(peer.getXVertices(), count);
            yVertices = Arrays.copyOf(peer.getYVertices(), count);
            zVertices = Arrays.copyOf(peer.getZVertices(), count);
            initialXVertices = xVertices;
            initialZVertices = zVertices;
            inited = true;
        }

    }

    public int[] getXVertices(){// TODO: 6/12/2017 Rename
        return xVertices;
    }

    public int[] getYVertices(){// TODO: 6/12/2017 Rename
        return yVertices;
    }

    public int[] getZVertices(){// TODO: 6/12/2017 Rename
        return zVertices;
    }

    public int[] getXTriangles(){
        return xTriangles;
    }

    public int[] getYTriangles(){
        return yTriangles;
    }

    public int[] getZTriangles(){
        return zTriangles;
    }

    public Model place(int strictX, int strictY) {
        this.strictX = strictX;
        this.strictY = strictY;
        return this;
    }

    public Model rotateTo(int orientation) {
        if (!inited) return this;
        initialXVertices = new int[xVertices.length];
        initialZVertices = new int[zVertices.length];
        initialXVertices = Arrays.copyOfRange(this.xVertices, 0, xVertices.length);
        initialZVertices = Arrays.copyOfRange(this.zVertices, 0, zVertices.length);
        xVertices = new int[initialXVertices.length];
        zVertices = new int[initialZVertices.length];
        int theta = orientation & 0x3fff;
        int sin = Projection.SINE[theta];
        int cos = Projection.COSINE[theta];
        for (int i = 0; i < initialXVertices.length; ++i) {
            xVertices[i] = (initialXVertices[i] * cos + initialZVertices[i] * sin >> 15) >> 1;
            zVertices[i] = (initialZVertices[i] * cos - initialXVertices[i] * sin >> 15) >> 1;
        }
        return this;
    }

    public Stream<Point> streamPoints() {
        final Stream.Builder<Point> points = Stream.builder();
        for (int i = 0; i < xTriangles.length; i++) {
            if (xTriangles[i] >= xVertices.length || yTriangles[i] >= xVertices.length || zTriangles[i] >= xVertices.length) {
                break;
            }
            Point x = Projection.worldToScreen(
                    strictX + xVertices[xTriangles[i]],
                    strictY + zVertices[xTriangles[i]],
                    -yVertices[xTriangles[i]]
            ).orElse(null);
            Point y = Projection.worldToScreen(
                    strictX + xVertices[yTriangles[i]],
                    strictY + zVertices[yTriangles[i]],
                    -yVertices[yTriangles[i]]
            ).orElse(null);
            Point z = Projection.worldToScreen(
                    strictX + xVertices[zTriangles[i]],
                    strictY + zVertices[zTriangles[i]],
                    -yVertices[zTriangles[i]]
            ).orElse(null);
            if (x != null && y != null && z != null
                    && x.x > 0 && x.y > 0
                    && y.x > 0 && y.y > 0
                    && z.x > 0 && z.y > 0) {
        /*        y.x += 4;
                y.y += 4;*/
                points.add(x);
                points.add(y);
                points.add(z);
            }
        }
        return points.build();
    }

    public Stream<Polygon> streamPolygons() {
        final Stream.Builder<Polygon> polygons = Stream.builder();
        if (!inited) return polygons.build();

        for (int i = 0; i < xTriangles.length; i++) {
            if (xTriangles[i] >= xVertices.length || yTriangles[i] >= xVertices.length || zTriangles[i] >= xVertices.length) {
                break;
            }
            Point x = Projection.worldToScreen(
                    strictX + xVertices[xTriangles[i]],
                    strictY + zVertices[xTriangles[i]],
                    -yVertices[xTriangles[i]]
            ).orElse(null);
            Point y = Projection.worldToScreen(
                    strictX + xVertices[yTriangles[i]],
                    strictY + zVertices[yTriangles[i]],
                    -yVertices[yTriangles[i]]
            ).orElse(null);
            Point z = Projection.worldToScreen(
                    strictX + xVertices[zTriangles[i]],
                    strictY + zVertices[zTriangles[i]],
                    -yVertices[zTriangles[i]]
            ).orElse(null);
            if (x != null && y != null && z != null
                    && x.x > 0 && x.y > 0
                    && y.x > 0 && y.y > 0
                    && z.x > 0 && z.y > 0) {
                y.x += 4;
                y.y += 4;
                polygons.add(new Polygon(new int[]{x.x, y.x, z.x}, new int[]{x.y, y.y, z.y}, 3));
            }
        }
        return polygons.build();
    }


    @NotNull
    public RSModel getRsModel() {
        return rsModel;
    }
}
