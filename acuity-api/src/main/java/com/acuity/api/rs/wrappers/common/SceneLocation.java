package com.acuity.api.rs.wrappers.common;

import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.utils.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Eclipseop.
 * Date: 6/8/2017.
 */
public class SceneLocation implements Locatable {

	private static final Logger logger = LoggerFactory.getLogger(SceneLocation.class);

	private int baseX, baseY;
	private int sceneX;
	private int sceneY;
	private int plane;

	public SceneLocation(int sceneX, int sceneY, int plane) {
		this(sceneX, sceneY, plane, Scene.getBaseX(), Scene.getBaseX());
	}

	public SceneLocation(int sceneX, int sceneY, int plane, int baseX, int baseY) {
		this.sceneX = sceneX;
		this.sceneY = sceneY;
		this.plane = plane;
		this.baseX = baseX;
		this.baseY = baseY;
	}

	public boolean isLoaded() {
		return getSceneX() > 3 && getSceneX() <= 98 && getSceneY() > 3 && getSceneY() <= 98;
	}

	public int getSceneX() {
		return sceneX;
	}

	public int getSceneY() {
		return sceneY;
	}

	public int getBaseX() {
		return baseX;
	}

	public int getBaseY() {
		return baseY;
	}

	@Override
	public int getPlane() {
		return plane;
	}

	public StrictLocation getStrictLocation(){
        return new StrictLocation(getSceneX() * 128, getSceneY() * 128, getPlane(), getBaseX(), getBaseY());
    }

	@Override
	public WorldLocation getWorldLocation() {
		return new WorldLocation(getSceneX() + getBaseX(), getSceneY() + getBaseY(), getPlane());
	}
}
