package com.acuity.api.rs.wrappers.common.locations.screen;

import com.acuity.api.rs.utils.Projection;

/**
 * Created by Zachary Herridge on 7/10/2017.
 */
public class ScreenLocation3D extends ScreenLocation {

    private int z;

    public ScreenLocation3D(int x, int y, int z) {
        super(x, y);
        this.z = z;
    }

    public int getZ() {
        return z;
    }

    public ScreenLocation3D rotate(int orientation) {
        int sin = Projection.SINE[orientation];
        int cos = Projection.COSINE[orientation];
        return new ScreenLocation3D(x * cos + z * sin >> 16, y, z * cos - x * sin >> 16);
    }

    @Override
    public int[] toArray() {
        return new int[]{x, y, z};
    }
}
