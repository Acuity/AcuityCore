package com.acuity.rs.api;

import com.acuity.api.rs.wrappers.mobile.Player;
import java.lang.String;

//Generated

public interface RSPlayer extends RSActor {

    String[] getActions();

    RSPlayerComposite getAppearance();

    int getCombatLevel();

    RSModel getModel();

    String getName();

    int getPrayerIcon();

    int getSkullIcon();

    int getTeam();

    int getTotalLevel();

    Player getWrapper();
}
