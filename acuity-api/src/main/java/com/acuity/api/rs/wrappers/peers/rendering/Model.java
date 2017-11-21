package com.acuity.api.rs.wrappers.peers.rendering;

import com.acuity.api.AcuityInstance;
import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.utils.Camera;
import com.acuity.api.rs.utils.Projection;
import com.acuity.api.rs.utils.Random;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation3D;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenPolygon;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenTriangle;
import com.acuity.rs.api.RSModel;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Zachary Herridge on 6/12/2017.
 */
public class Model extends Renderable {

    private static final Logger logger = LoggerFactory.getLogger(Model.class);

    private RSModel rsModel;

    private int fineXCached, fineYCached;

    private boolean modelCached = false;
    private int orientationCached = 0;

    private List<ScreenTriangle> triangles;
    private List<ScreenLocation3D> vertices;

    @ClientInvoked
    public Model(RSModel peer) {
        super(peer);
        this.rsModel = Preconditions.checkNotNull(peer);

        if (AcuityInstance.getSettings().isModelCachingEnabled() && peer.getXTriangles() != null && peer.getXVertices() != null) {
            int[] xTriangles = peer.getXTriangles();
            int[] yTriangles = peer.getYTriangles();
            int[] zTriangles = peer.getZTriangles();
            int[] xVertices = peer.getXVertices();
            int[] yVertices = peer.getYVertices();
            int[] zVertices = peer.getZVertices();

            vertices = new ArrayList<>(xVertices.length);
            for (int i = 0; i < xVertices.length; ++i) {
                vertices.add(new ScreenLocation3D(xVertices[i], yVertices[i], zVertices[i]));
            }

            triangles = new ArrayList<>(xTriangles.length);
            for (int i = 0; i < xTriangles.length; ++i) {
                int triangleX = xTriangles[i];
                int triangleY = yTriangles[i];
                int triangleZ = zTriangles[i];
                ScreenTriangle triangle = new ScreenTriangle(vertices.get(triangleX), vertices.get(triangleY), vertices.get(triangleZ));
                triangles.add(triangle);
            }

            modelCached = true;
        }
    }

    public List<ScreenLocation3D> getVertices() {
        return vertices;
    }

    @Override
    public Supplier<ScreenPolygon> getProjectionSupplier() {
        return () -> Random.nextElement(getPolygons());
    }

    public int getFineXCached() {
        return fineXCached;
    }

    public int getFineYCached() {
        return fineYCached;
    }

    public boolean isValid() {
        return modelCached;
    }

    public Model place(int fineX, int fineY) {
        this.fineXCached = fineX;
        this.fineYCached = fineY;
        return this;
    }

    private List<ScreenTriangle> rotate(List<ScreenTriangle> triangles, int orientation) {
        List<ScreenTriangle> rotatedTriangles = new ArrayList<>();
        for (ScreenTriangle triangle : triangles) {
            ScreenLocation3D a = (ScreenLocation3D) triangle.getP1();
            ScreenLocation3D b = (ScreenLocation3D) triangle.getP2();
            ScreenLocation3D c = (ScreenLocation3D) triangle.getP3();

            ScreenTriangle rotatedTriangle = new ScreenTriangle(a.rotate(orientation), b.rotate(orientation), c.rotate(orientation));
            rotatedTriangles.add(rotatedTriangle);
        }
        return rotatedTriangles;
    }

    public int getCachedOrientation() {
        return orientationCached;
    }

    public Model rotateTo(int orientation) {
        orientation = (orientation + 1024) % 2048;
        if (orientation != 0) {
            triangles = rotate(triangles, orientation);
        }

        return this;
    }

    public List<ScreenPolygon> getPolygons() {
        int localX = fineXCached;
        int localY = fineYCached;


        List<ScreenPolygon> polygons = new ArrayList<>();
        for (ScreenTriangle triangle : triangles) {
            ScreenLocation3D vx = (ScreenLocation3D) triangle.getP1();
            ScreenLocation3D vy = (ScreenLocation3D) triangle.getP2();
            ScreenLocation3D vz = (ScreenLocation3D) triangle.getP3();

            ScreenLocation x = Projection.fineToScreen(localX - vx.getX(), localY - vx.getZ(), -vx.getY()).orElse(null);
            ScreenLocation y = Projection.fineToScreen(localX - vy.getX(), localY - vy.getZ(), -vy.getY()).orElse(null);
            ScreenLocation z = Projection.fineToScreen(localX - vz.getX(), localY - vz.getZ(), -vz.getY()).orElse(null);

            polygons.add(new ScreenPolygon(x, y, z));
        }

        return polygons;
    }

    public RSModel getRsModel() {
        return rsModel;
    }
}
