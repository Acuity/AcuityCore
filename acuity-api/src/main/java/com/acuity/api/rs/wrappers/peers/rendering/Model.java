package com.acuity.api.rs.wrappers.peers.rendering;

import com.acuity.api.AcuityInstance;
import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.utils.HullUtil;
import com.acuity.api.rs.utils.Projection;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.Screen3DLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenPolygon;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.Screen3DTriangle;
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

    private List<Screen3DTriangle> triangles;
    private List<Screen3DLocation> vertices;

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
                vertices.add(new Screen3DLocation(xVertices[i], yVertices[i], zVertices[i]));
            }

            int[] xTriangles = peer.getXTriangles();
            int[] yTriangles = peer.getYTriangles();
            int[] zTriangles = peer.getZTriangles();

            triangles = new ArrayList<>(xTriangles.length);
            for (int i = 0; i < xTriangles.length; ++i) {
                triangles.add(new Screen3DTriangle(vertices.get(xTriangles[i]), vertices.get(yTriangles[i]), vertices.get(zTriangles[i])));
            }

            modelCached = true;
        }
    }

    public List<Screen3DLocation> getVertices() {
        return vertices;
    }

    public List<Screen3DTriangle> getTriangles() {
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

    private List<Screen3DTriangle> rotate(List<Screen3DTriangle> triangles, int orientation) {
        List<Screen3DTriangle> rotatedTriangles = new ArrayList<>();
        for (Screen3DTriangle triangle : triangles) {
            Screen3DTriangle rotatedTriangle = new Screen3DTriangle(
                    triangle.getP1().rotate(orientation),
                    triangle.getP2().rotate(orientation),
                    triangle.getP3().rotate(orientation));
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
        for (Screen3DTriangle triangle : triangles) {
            Screen3DLocation xVertex = triangle.getP1();
            Screen3DLocation yVertex = triangle.getP2();
            Screen3DLocation zVertex = triangle.getP3();

            ScreenLocation x = Projection.fineToScreen(fineXCached - xVertex.getX(), fineYCached - xVertex.getZ(), -xVertex.getY()).orElse(null);
            ScreenLocation y = Projection.fineToScreen(fineXCached - yVertex.getX(), fineYCached - yVertex.getZ(), -yVertex.getY()).orElse(null);
            ScreenLocation z = Projection.fineToScreen(fineXCached - zVertex.getX(), fineYCached - zVertex.getZ(), -zVertex.getY()).orElse(null);

            if (x == null || y == null || z == null) continue;
            polygons.add(new ScreenPolygon(x, y, z));
        }

        return polygons;
    }

    public RSModel getRsModel() {
        return rsModel;
    }
}
