package com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.interfaces.Clickable;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation3D;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocationShape;
import com.acuity.rs.api.RSAxisAlignedBoundingBox;

import java.util.function.Supplier;

/**
 * Created by Zachary Herridge on 7/10/2017.
 */
public class AxisAlignedBoundingBox extends BoundingBox implements Clickable{

    private RSAxisAlignedBoundingBox rsAxisAlignedBoundingBox;

    @ClientInvoked
    public AxisAlignedBoundingBox(RSAxisAlignedBoundingBox AxisAlignedBoundingBox) {
        super(AxisAlignedBoundingBox);
        this.rsAxisAlignedBoundingBox = AxisAlignedBoundingBox;
    }

    public RSAxisAlignedBoundingBox getRsAxisAlignedBoundingBox() {
        return rsAxisAlignedBoundingBox;
    }

    public ScreenLocation3D getMin(){
        return new ScreenLocation3D(rsAxisAlignedBoundingBox.getMinX(), rsAxisAlignedBoundingBox.getMinY(), rsAxisAlignedBoundingBox.getMinZ());
    }

    public ScreenLocation3D getMax(){
        return new ScreenLocation3D(rsAxisAlignedBoundingBox.getMaxX(), rsAxisAlignedBoundingBox.getMaxY(), rsAxisAlignedBoundingBox.getMaxZ());
    }

    public ScreenLocation3D[] getVertices(){
        ScreenLocation3D min = getMin();
        ScreenLocation3D max = getMax();
        return new ScreenLocation3D[]{
                new ScreenLocation3D(min.getX(), min.getY(), min.getZ()),
                new ScreenLocation3D(min.getX(), min.getY(), max.getZ()),
                new ScreenLocation3D(max.getX(), min.getY(), max.getZ()),
                new ScreenLocation3D(max.getX(), min.getY(), min.getZ()),
                new ScreenLocation3D(min.getX(), max.getY(), min.getZ()),
                new ScreenLocation3D(min.getX(), max.getY(), max.getZ()),
                new ScreenLocation3D(max.getX(), max.getY(), max.getZ()),
                new ScreenLocation3D(max.getX(), max.getY(), min.getZ()),
        };
    }

    @Override
    public Supplier<ScreenLocationShape> getScreenTargetSupplier() {
        return null; // TODO: 11/20/2017  
    }
}
