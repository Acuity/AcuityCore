package com.acuity.api.rs.query;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.interfaces.Locatable;
import com.acuity.api.rs.wrappers.mobile.Npc;
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
        logger.debug("Returning nearest Ncp matching predicate.");
		return streamLoaded()
                .filter(predicate)
                .sorted(Comparator.comparingInt(Locatable::distance))
                .findFirst();
	}

	public static Optional<Npc> getNearest(final String name) {
	    logger.debug("Returning nearest Ncp with name '{}'", name);
		return getNearest(p -> p.getNullSafeName().equalsIgnoreCase(name));
	}

	public static Optional<Npc> getNearest(final int id) {
        logger.debug("Returning nearest Ncp with id '{}'", id);
		return getNearest(p -> p.getId() == id);
	}
}
