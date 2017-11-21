package com.acuity.api.rs.wrappers.common.locations.screen;

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

    @Override
    public int[] toArray() {
        return new int[]{x, y, z};
    }
}
