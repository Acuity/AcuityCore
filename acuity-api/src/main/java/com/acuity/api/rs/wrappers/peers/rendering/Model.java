package com.acuity.api.rs.wrappers.peers.rendering;

import com.acuity.api.AcuityInstance;
import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.utils.HullUtil;
import com.acuity.api.rs.utils.Projection;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation3D;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenPolygon;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenTriangle;
import com.acuity.rs.api.RSModel;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
            int[] xVertices = peer.getXVertices();
            int[] yVertices = peer.getYVertices();
            int[] zVertices = peer.getZVertices();

            vertices = new ArrayList<>(xVertices.length);
            for (int i = 0; i < xVertices.length; ++i) {
                vertices.add(new ScreenLocation3D(xVertices[i], yVertices[i], zVertices[i]));
            }

            int[] xTriangles = peer.getXTriangles();
            int[] yTriangles = peer.getYTriangles();
            int[] zTriangles = peer.getZTriangles();

            triangles = new ArrayList<>(xTriangles.length);
            for (int i = 0; i < xTriangles.length; ++i) {
                triangles.add(new ScreenTriangle(vertices.get(xTriangles[i]), vertices.get(yTriangles[i]), vertices.get(zTriangles[i])));
            }

            modelCached = true;
        }
    }

    public List<ScreenLocation3D> getVertices() {
        return vertices;
    }

    public List<ScreenTriangle> getTriangles() {
        return triangles;
    }

    @Override
    public Supplier<ScreenPolygon> getProjectionSupplier() {
        List<ScreenLocation> collect = getPolygons().stream()
                .map(screenPolygon -> Arrays.stream(screenPolygon.getLocations()))
                .flatMap(Function.identity())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return () -> HullUtil.convexHull(collect, true);
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
            ScreenTriangle rotatedTriangle = new ScreenTriangle(
                    ((ScreenLocation3D) triangle.getP1()).rotate(orientation),
                    ((ScreenLocation3D) triangle.getP2()).rotate(orientation),
                    ((ScreenLocation3D) triangle.getP3()).rotate(orientation));
            rotatedTriangles.add(rotatedTriangle);
        }
        return rotatedTriangles;
    }

    public int getCachedOrientation() {
        return orientationCached;
    }

    public Model rotateTo(int orientation) {
        orientationCached = (orientation + 1024) % 2048;
        if (isValid() && orientationCached != 0) triangles = rotate(triangles, orientationCached);
        return this;
    }

    public List<ScreenPolygon> getPolygons() {
        List<ScreenPolygon> polygons = new ArrayList<>();

        if (!isValid()) return polygons;

        for (ScreenTriangle triangle : triangles) {
            ScreenLocation3D xVertex = (ScreenLocation3D) triangle.getP1();
            ScreenLocation3D yVertex = (ScreenLocation3D) triangle.getP2();
            ScreenLocation3D zVertex = (ScreenLocation3D) triangle.getP3();

            ScreenLocation x = Projection.fineToScreen(fineXCached - xVertex.getX(), fineYCached - xVertex.getZ(), -xVertex.getY()).orElse(null);
            ScreenLocation y = Projection.fineToScreen(fineXCached - yVertex.getX(), fineYCached - yVertex.getZ(), -yVertex.getY()).orElse(null);
            ScreenLocation z = Projection.fineToScreen(fineXCached - zVertex.getX(), fineYCached - zVertex.getZ(), -zVertex.getY()).orElse(null);

            polygons.add(new ScreenTriangle(x, y, z));
        }

        return polygons;
    }

    public RSModel getRsModel() {
        return rsModel;
    }
}
