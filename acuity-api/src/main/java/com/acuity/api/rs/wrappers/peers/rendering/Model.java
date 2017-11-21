package com.acuity.api.rs.wrappers.peers.rendering;

import com.acuity.api.AcuityInstance;
import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.utils.Projection;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocationShape;
import com.acuity.rs.api.RSModel;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by Zachary Herridge on 6/12/2017.
 */
public class Model extends Renderable{

    private static final Logger logger = LoggerFactory.getLogger(Model.class);

    private RSModel rsModel;

    private int fineXCached, fineYCached;

    private boolean modelCached = false;
    private int[] xVertices, yVertices, zVertices;
    private int[] xTriangles, yTriangles, zTriangles;
    private int[] initialXVertices, initialZVertices;
    private int orientationCached = 0;

    @ClientInvoked
    public Model(RSModel peer) {
        super(peer);
        this.rsModel = Preconditions.checkNotNull(peer);

        if (AcuityInstance.getSettings().isModelCachingEnabled() && peer.getXTriangles() != null && peer.getXVertices() != null){
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
            modelCached = true;
        }
    }

    @Override
    public Supplier<ScreenLocationShape> getScreenTargetSupplier() {
        return () -> streamPoints().findAny().map(ScreenLocationShape::new).orElse(null);
    }

    public int getFineXCached() {
        return fineXCached;
    }

    public int getFineYCached() {
        return fineYCached;
    }

    public int[] getXVertices(){
        return xVertices;
    }

    public int[] getYVertices(){
        return yVertices;
    }

    public int[] getZVertices(){
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

    public boolean isValid() {
        return modelCached;
    }

    public Model place(int fineX, int fineY) {
        this.fineXCached = fineX;
        this.fineYCached = fineY;
        return this;
    }

    public Model rotateTo(int orientation) {
        this.orientationCached = orientation;

        if (!isValid()) return this;

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

    public int getCachedOrientation() {
        return orientationCached;
    }

    public Stream<ScreenLocation> streamPoints() {
        if (!isValid()) throw new IllegalStateException("Cannot stream model as points when model was not cached.");

        final Stream.Builder<ScreenLocation> points = Stream.builder();
        for (int i = 0; i < xTriangles.length; i++) {
            if (xTriangles[i] >= xVertices.length || yTriangles[i] >= xVertices.length || zTriangles[i] >= xVertices.length) {
                break;
            }
            ScreenLocation x = Projection.fineToScreen(
                    fineXCached + xVertices[xTriangles[i]],
                    fineYCached + zVertices[xTriangles[i]],
                    -yVertices[xTriangles[i]]
            ).orElse(null);
            ScreenLocation y = Projection.fineToScreen(
                    fineXCached + xVertices[yTriangles[i]],
                    fineYCached + zVertices[yTriangles[i]],
                    -yVertices[yTriangles[i]]
            ).orElse(null);
            ScreenLocation z = Projection.fineToScreen(
                    fineXCached + xVertices[zTriangles[i]],
                    fineYCached + zVertices[zTriangles[i]],
                    -yVertices[zTriangles[i]]
            ).orElse(null);
            if (x != null && y != null && z != null
                    && x.getX() > 0 && x.getY() > 0
                    && y.getX() > 0 && y.getY() > 0
                    && z.getX() > 0 && z.getY() > 0) {
                y.increment(0, 4);
                x.increment(0, 4);

                points.add(x);
                points.add(y);
                points.add(z);
            }
        }
        return points.build();
    }

    public Stream<ScreenLocationShape> streamScreenShapes() {
      /*  if (!isValid()) throw new IllegalStateException("Cannot stream model as polygons when model was not cached.");

        final Stream.Builder<ScreenLocationShape<ScreenLocation3D>> locationShapeBuilder = Stream.builder();
        for (int i = 0; i < xTriangles.length; i++) {
            if (xTriangles[i] >= xVertices.length || yTriangles[i] >= xVertices.length || zTriangles[i] >= xVertices.length) {
                break;
            }
            ScreenLocation x = Projection.fineToScreen(
                    fineXCached + xVertices[xTriangles[i]],
                    fineYCached + zVertices[xTriangles[i]],
                    -yVertices[xTriangles[i]]
            ).orElse(null);
            ScreenLocation y = Projection.fineToScreen(
                    fineXCached + xVertices[yTriangles[i]],
                    fineYCached + zVertices[yTriangles[i]],
                    -yVertices[yTriangles[i]]
            ).orElse(null);
            ScreenLocation z = Projection.fineToScreen(
                    fineXCached + xVertices[zTriangles[i]],
                    fineYCached + zVertices[zTriangles[i]],
                    -yVertices[zTriangles[i]]
            ).orElse(null);
            if (x != null && y != null && z != null
                    && x.getX() > 0 && x.getY() > 0
                    && y.getX() > 0 && y.getY() > 0
                    && z.getX() > 0 && z.getY() > 0) {
                y.increment(0, 4);
                x.increment(0, 4);

                locationShapeBuilder.add(new ScreenLocationShape<>(
                        new ScreenLocation3D(x.getX(), y.getX(), z.getX()),
                        new ScreenLocation3D(x.getY(), y.getY(), z.getY())
                ));
            }
        }

        return locationShapeBuilder.build();*/
        // TODO: 11/21/2017  
      return null;
    }



    public RSModel getRsModel() {
        return rsModel;
    }
}
