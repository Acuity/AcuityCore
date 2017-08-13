package com.acuity.api.rs.utils.task.login;

import com.acuity.api.AcuityInstance;
import com.acuity.api.input.direct.mouse.Mouse;
import com.acuity.api.rs.utils.Delay;
import com.acuity.api.rs.utils.Game;
import com.acuity.api.rs.utils.Login;
import com.acuity.api.rs.utils.Timer;
import com.acuity.api.rs.utils.task.Task;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Eclipseop.
 * Date: 7/8/2017.
 */
public class LoginTask extends Task {

	private static final Logger logger = LoggerFactory.getLogger(LoginTask.class);

	private static final Timer tooManyLogins = new Timer();

	@Override
	public boolean validate() {
		if (tooManyLogins.isRunning()) {
			return false;
		}

		return Game.State.LOGIN_SCREEN.isCurrent();
	}

	@Override
	public int loop() {
		if (AcuityInstance.getClient().isWorldSelectOpen()) {
			Mouse.click(new ScreenLocation(741, 11));
			Delay.delayUntil(() -> !AcuityInstance.getClient().isWorldSelectOpen(), 750);
		}

		final Account account = AcuityInstance.getRsAccount();
		if (account != null && account.getUsername() != null && account.isValid()) {
			switch (Login.getLoginState()) {
				case ENTER_CREDENTIALS:
					final String loginMessage = Login.getLoginMessage();
					if (loginMessage.contains("many login attempts")) {
						logger.info("Too many login attempts! Sleeping for 2 minutes.");
						AcuityInstance.getClient().getRsClient().setLoginResponse1("Waiting...");
						tooManyLogins.restart(120000);
					} else if (loginMessage.contains("disabled")) {
						logger.info("Account " + account.getUsername() + " is disabled. :(");
						account.setBanned(true);
					} else if (loginMessage.contains("stolen")) {
						logger.info("Account " + account.getUsername() + " is locked (not disabled).");
						account.setLocked(true);
					} else {
						Login.setLoginInfo(account);
						ScreenLocation screenLocation = new ScreenLocation((int) (235 + (Math.random() * (370 - 235))), (int) (305 + (Math.random() * (335 - 305))));
						Mouse.click(screenLocation);
					}
					break;
				case INVALID_CREDENTIALS:
					account.setWrongLogin(true);
					Login.setLoginState(Login.State.ENTER_CREDENTIALS);
					break;
				default:
					Login.setLoginState(Login.State.ENTER_CREDENTIALS);
					break;
			}
		}

		return 600;
	}
}
