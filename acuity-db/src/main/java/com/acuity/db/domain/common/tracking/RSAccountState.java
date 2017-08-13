package com.acuity.db.domain.common.tracking;

import java.util.HashMap;

/**
 * Created by Zach on 8/13/2017.
 */
public class RSAccountState {

    private HashMap<String, Integer> skillExperience = new HashMap<>();
    private int hpPercent;
    private int prayerPoints;
    private int runEnergy;
    private String tile;

    public HashMap<String, Integer> getSkillExperience() {
        return skillExperience;
    }

    public int getHpPercent() {
        return hpPercent;
    }

    public void setHpPercent(int hpPercent) {
        this.hpPercent = hpPercent;
    }

    public int getPrayerPoints() {
        return prayerPoints;
    }

    public void setPrayerPoints(int prayerPoints) {
        this.prayerPoints = prayerPoints;
    }

    public int getRunEnergy() {
        return runEnergy;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public void setRunEnergy(int runEnergy) {
        this.runEnergy = runEnergy;
    }
}
