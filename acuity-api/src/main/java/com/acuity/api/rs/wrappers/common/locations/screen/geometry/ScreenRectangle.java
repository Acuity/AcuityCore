package com.acuity.api.rs.wrappers.common.locations.screen.geometry;

import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;

public class ScreenRectangle extends ScreenPolygon {

    private final ScreenLocation root;
    private final int width;
    private final int height;

    public ScreenRectangle(ScreenLocation root, int width, int height) {
        super(convert(root, width, height));
        this.root = root;
        this.width = width;
        this.height = height;
    }

    public ScreenRectangle(int x, int y, int width, int height){
        this(new ScreenLocation(x, y), width, height);
    }

    public ScreenRectangle(ScreenLocation low, ScreenLocation high) {
        this(low, high.getX() - low.getX(), high.getY() - low.getY());
    }

    private static ScreenLocation[] convert(ScreenLocation p1, int width, int height) {
        ScreenLocation p2 = p1.transform(width, 0);
        ScreenLocation p3 = p2.transform(0, height);
        ScreenLocation p4 = p3.transform(-width, 0);
        return new ScreenLocation[]{p1, p2, p3, p4};
    }

    public ScreenLocation getRoot() {
        return root;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public double size() {
        return width * height;
    }
}

