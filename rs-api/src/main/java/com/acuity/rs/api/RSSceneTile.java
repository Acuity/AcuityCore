package com.acuity.rs.api;

//Generated

public interface RSSceneTile extends RSNode {

    RSSceneBoundaryDecor getBoundaryDecor();

    RSSceneTileDecor getGroundObject();

    RSItemLayer getItemLayer();

    RSSceneElement[] getMarkers();

    RSSceneTileModel getModel();

    RSSceneTilePaint getPaint();

    int getPlane();

    int getSceneX();

    int getSceneY();

    RSSceneBoundary getWallObject();
}
