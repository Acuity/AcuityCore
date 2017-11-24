package com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.interfaces.Projectable;
import com.acuity.api.rs.wrappers.common.locations.screen.Screen3DLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.geometry.ScreenPolygon;
import com.acuity.rs.api.RSAxisAlignedBoundingBox;

import java.util.function.Supplier;

/**
 * Created by Zachary Herridge on 7/10/2017.
 */
public class AxisAlignedBoundingBox extends BoundingBox implements Projectable {

    private RSAxisAlignedBoundingBox rsAxisAlignedBoundingBox;

    @ClientInvoked
    public AxisAlignedBoundingBox(RSAxisAlignedBoundingBox AxisAlignedBoundingBox) {
        super(AxisAlignedBoundingBox);
        this.rsAxisAlignedBoundingBox = AxisAlignedBoundingBox;
    }

    public RSAxisAlignedBoundingBox getRsAxisAlignedBoundingBox() {
        return rsAxisAlignedBoundingBox;
    }

    public Screen3DLocation getMin(){
        return new Screen3DLocation(rsAxisAlignedBoundingBox.getMinX(), rsAxisAlignedBoundingBox.getMinY(), rsAxisAlignedBoundingBox.getMinZ());
    }

    public Screen3DLocation getMax(){
        return new Screen3DLocation(rsAxisAlignedBoundingBox.getMaxX(), rsAxisAlignedBoundingBox.getMaxY(), rsAxisAlignedBoundingBox.getMaxZ());
    }

    public Screen3DLocation[] getVertices(){
        Screen3DLocation min = getMin();
        Screen3DLocation max = getMax();
        return new Screen3DLocation[]{
                new Screen3DLocation(min.getX(), min.getY(), min.getZ()),
                new Screen3DLocation(min.getX(), min.getY(), max.getZ()),
                new Screen3DLocation(max.getX(), min.getY(), max.getZ()),
                new Screen3DLocation(max.getX(), min.getY(), min.getZ()),
                new Screen3DLocation(min.getX(), max.getY(), min.getZ()),
                new Screen3DLocation(min.getX(), max.getY(), max.getZ()),
                new Screen3DLocation(max.getX(), max.getY(), max.getZ()),
                new Screen3DLocation(max.getX(), max.getY(), min.getZ()),
        };
    }

    @Override
    public Supplier<ScreenPolygon> getProjectionSupplier() {
        return null; // TODO: 11/20/2017  
    }
}
