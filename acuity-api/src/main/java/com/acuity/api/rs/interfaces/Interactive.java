package com.acuity.api.rs.interfaces;

import com.acuity.api.input.interactor.Interactor;
import com.acuity.api.rs.utils.ActionResult;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Eclipseop.
 * Date: 6/8/2017.
 */
public interface Interactive extends Projectable {

	default List<String> getActions() {
		return Collections.emptyList();
	}

	default boolean isActionPresent(String action) {
		return getActions(testAction -> testAction.toLowerCase().contains(action.toLowerCase())).size() > 0;
	}

	default List<String> getActions(Predicate<String> predicate) {
		return getActions().stream()
				.filter(Objects::nonNull)
				.filter(predicate)
				.collect(Collectors.toList());
	}

	default ActionResult interact(String action){
		return Interactor.interact(this, action);
	}
}
