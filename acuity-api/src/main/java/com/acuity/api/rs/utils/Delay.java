package com.acuity.api.rs.utils;

import java.util.function.BooleanSupplier;

/**
 * Created by Eclipseop.
 * Date: 6/17/2017.
 */
public class Delay {

	private static int delayUntilSleepMS = 290;

	public static boolean delay(int durationMills) {
		if (durationMills <= 0) {
			return true;
		}
		try {
			Thread.sleep(durationMills);
		} catch (InterruptedException ignored) {
		}

		return true;
	}

	public static boolean delay(int minDurationMills, int maxDurationMills) {
		return delay(Random.nextInt(minDurationMills, maxDurationMills));
	}

	public static boolean delayUntil(BooleanSupplier condition, long timeoutMills) {
		timeoutMills = System.currentTimeMillis() + timeoutMills;
		while (System.currentTimeMillis() < timeoutMills && !Thread.interrupted()){
			if (condition.getAsBoolean()){
				return true;
			}
			delay(delayUntilSleepMS);
		}
		return false;
	}

	public static int getDelayUntilSleepMS() {
		return delayUntilSleepMS;
	}

	public static void setDelayUntilSleepMS(int delayUntilSleepMS) {
		Delay.delayUntilSleepMS = delayUntilSleepMS;
	}
}
