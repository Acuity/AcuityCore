package com.acuity.api.rs.query;

import com.acuity.api.AcuityInstance;
import com.acuity.api.rs.wrappers.mobile.Player;
import com.acuity.api.rs.utils.LocalPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Eclipseop.
 * Date: 6/9/2017.
 */
public class Players {

	private static Logger logger = LoggerFactory.getLogger(Players.class);

	public static Stream<Player> streamLoaded() {
		return Arrays.stream(AcuityInstance.getClient().getPlayers())
				.filter(Objects::nonNull);
	}

	public static List<Player> getLoaded(final Predicate<? super Player> predicate) {
        logger.trace("Returning Players(s) matching predicate.");
		return streamLoaded()
				.filter(predicate::test)
				.collect(Collectors.toList());
	}

	public static Optional<Player> getFirst(final Predicate<? super Player> predicate){
        logger.trace("Returning first Players matching predicate.");
        return streamLoaded()
                .filter(predicate::test)
                .findFirst();
    }

	public static List<Player> getLoaded() {
		return getLoaded(p -> true);
	}

	public static List<Player> getLoaded(final String... displayNames) {
        final List<String> names = Arrays.asList(displayNames);
        logger.debug("Returning Player(s) with names in {}", names);
        return getLoaded(player -> names.contains(player.getName()));
	}

    public static Optional<Player> getFirst(final String... displayNames) {
        final List<String> names = Arrays.asList(displayNames);
        logger.debug("Returning first Player with name in {}", names);
        return getFirst(player -> names.contains(player.getName()));
    }

	public static Optional<Player> getLocal() {
	    return LocalPlayer.get();
	}
}
