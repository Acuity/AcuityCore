package com.acuity.api.rs.query;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.wrappers.common.locations.SceneLocation;
import com.acuity.api.rs.wrappers.peers.scene.actors.impl.Npc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Eclipseop.
 * Date: 6/8/2017.
 */
public class Npcs {

	private static Logger logger = LoggerFactory.getLogger(Npcs.class);

	public static Stream<Npc> streamLoaded() {
		return Arrays.stream(AcuityInstance.getClient().getNpcs())
                .filter(Objects::nonNull);
	}

	public static Stream<Npc> streamLoaded(SceneLocation sceneLocation) {
		return Arrays.stream(AcuityInstance.getClient().getNpcs())
				.filter(Objects::nonNull)
				.filter(npc -> sceneLocation.equals(npc.getSceneLocation()));
	}

	public static List<Npc> getLoaded() {
		return getLoaded(npc -> true);
	}

	public static List<Npc> getLoaded(final Predicate<? super Npc> predicate) {
        logger.trace("Returning Ncp(s) matching predicate.");
		return streamLoaded()
                .filter(predicate::test)
                .collect(Collectors.toList());
	}

	public static Optional<Npc> getNearest(final Predicate<? super Npc> predicate) {
        logger.trace("Returning nearest Ncp matching predicate.");
		return streamLoaded()
                .filter(predicate)
                .sorted(Comparator.comparingInt(Locatable::distance))
                .findFirst();
	}

	public static Optional<Npc> getNearest(final String name) {
	    logger.trace("Returning nearest Ncp with name '{}'", name);
		return getNearest(p -> p.getNullSafeName().trim().equalsIgnoreCase(name));
	}

	public static Optional<Npc> getNearest(final int id) {
        logger.trace("Returning nearest Ncp with id '{}'", id);
		return getNearest(p -> p.getID() == id);
	}
}
