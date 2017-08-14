package com.acuity.botcontrol.clients.dreambot;

import com.acuity.db.domain.vertex.impl.RSAccount;
import com.google.common.eventbus.Subscribe;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.utilities.Timer;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Application extends AbstractScript {

	private final Timer timer = new Timer();

    private DreambotControlScript dreambotControlScript;

    public Application(DreambotControlScript dreambotControlScript) {
        this.dreambotControlScript = dreambotControlScript;
    }

    @Subscribe
	public int onLoop() {
        RSAccount account = dreambotControlScript.getAccount();
        if (account == null){

        }
        else {
            switch (getClient().getLoginIndex()) {
                case 2:
                    switch (getClient().getLoginResponse()) {
                        case TOO_MANY_ATTEMPTS:
                            log("Too many login attempts! Sleeping for 2 minutes.");
                            clearText();
                            timer.setRunTime(120000);
                            timer.reset();
                            break;
                        case DISABLED:
                            // TODO: 8/13/2017 Banned Account
                            break;
                        case ACCOUNT_LOCKED:
                            // TODO: 8/13/2017 Locked
                            break;
                        default:
                            getKeyboard().type(account.getEmail());
                            getKeyboard().typeSpecialKey((char) KeyEvent.VK_TAB);
                            getKeyboard().type(getPassword(account));
                            getMouse().click(new Point((int) (235 + (Math.random() * (370 - 235))), (int) (305 + (Math.random() * (335 - 305)))));
                            break;
                    }
                case 3:
                    // TODO: 8/13/2017 Wrong login
                    getMouse().click(new Point(462, 290));
                    break;
                default:
                    getMouse().click(new Point(462, 290));
                    break;
            }
        }

		return 600;
	}

	private String getPassword(RSAccount rsAccount){
        return "";
    }

	private void clearText() {
		while (!getClient().getUsername().equals("")) {
			getKeyboard().typeSpecialKey((char) KeyEvent.VK_BACK_SPACE);
		}
		if (!getClient().getPassword().equals("")) {
			getKeyboard().typeSpecialKey((char) KeyEvent.VK_TAB);
			while (!getClient().getPassword().equals("")) {
				getKeyboard().typeSpecialKey((char) KeyEvent.VK_BACK_SPACE);
			}
			getKeyboard().typeSpecialKey((char) KeyEvent.VK_TAB);
		}
	}

}