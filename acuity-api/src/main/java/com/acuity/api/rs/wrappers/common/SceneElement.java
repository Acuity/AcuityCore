package com.acuity.api.rs.wrappers.common;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.interfaces.Interactive;
import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.interfaces.Nameable;
import com.acuity.api.rs.utils.UIDs;
import com.acuity.api.rs.utils.Varps;
import com.acuity.api.rs.wrappers.common.locations.FineLocation;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenPolygon;
import com.acuity.api.rs.wrappers.peers.engine.Varpbit;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.api.rs.wrappers.peers.rendering.bounding_boxes.AxisAlignedBoundingBox;
import com.acuity.api.rs.wrappers.peers.types.SceneElementType;
import com.acuity.rs.api.RSModel;
import com.acuity.rs.api.RSRenderable;
import com.acuity.rs.api.RSSceneElementType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by Zach on 6/24/2017.
 */
public interface SceneElement extends Locatable, Nameable, Interactive {

    static Optional<Model> getModel(RSRenderable rsRenderable, FineLocation location, Integer orientation) {
        if (rsRenderable == null) return Optional.empty();

        Model lastModel;
        if (rsRenderable instanceof RSModel) lastModel = new Model((RSModel) rsRenderable);
        else lastModel = rsRenderable.getCachedModel();

        return Optional.ofNullable(lastModel)
                .map(model -> model.place(location.getFineX() , location.getFineY()))
                .map(model -> {
                    if (orientation != null) model.rotateTo(orientation);
                    return model;
                });
    }

    @Override
    default List<String> getActions(){
        return getComposite().map(SceneElementType::getActions).map(Arrays::asList).orElse(Collections.emptyList());
    }

    @Override
    default Supplier<ScreenPolygon> getProjectionSupplier(){
        if (!AcuityInstance.getSettings().isModelInteractionsEnabled()){
            return () -> getBoundingBox().map(AxisAlignedBoundingBox::getProjectionSupplier).map(Supplier::get).orElse(null);
        }
        return () -> getModel().map(Model::getProjectionSupplier).map(Supplier::get).orElse(null);
    }

    Optional<AxisAlignedBoundingBox> getBoundingBox();

    Optional<Model> getModel();

    int getOrientation();

    int getID();

    int getFlag();

    UIDs.UID getUID();

    default String getName(){
        return getComposite().map(SceneElementType::getName).orElse(null);
    }

    default Optional<SceneElementType> getComposite(){
        RSSceneElementType rsSceneElementComposite = AcuityInstance.getClient().getRsClient().invokeLoadSceneElementType(getID());

        if (rsSceneElementComposite != null){
            int[] transformIDs = rsSceneElementComposite.getTransformIDs();

            if (transformIDs != null){
                int varpbitIndex = rsSceneElementComposite.getVarpbitIndex();
                int varpIndex = rsSceneElementComposite.getVarpIndex();

                int transformedIndex = -1;
                if (varpbitIndex != -1){
                    transformedIndex = Varps.getVarpBit(varpbitIndex).map(Varpbit::getValue).orElse(-1);
                }
                else if (varpIndex != -1){
                    transformedIndex = Varps.get(varpIndex, -1);
                }

                if (transformedIndex != -1) {
                    rsSceneElementComposite = AcuityInstance.getClient().getRsClient().invokeLoadSceneElementType(transformIDs[transformedIndex]);
                }
            }
        }

        return Optional.ofNullable(rsSceneElementComposite).map(RSSceneElementType::getWrapper);
    }
}
