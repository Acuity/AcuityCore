package com.acuity.db.domain.common.tracking;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Zach on 8/13/2017.
 */
public class StatLevel {

    private static HashMap<Integer, Integer> levelMap = new LinkedHashMap<>();

    static {
        int points = 0;
        int xp = 0;
        for (int lvl = 1; lvl <= 99; lvl++) {
            points += Math.floor(lvl + 300 * Math.pow(2, lvl / 7.));
            if (lvl >= 2) {
                levelMap.put(lvl, xp);
            }
            xp = (int) Math.floor(points / 4);
        }
    }

    public static int getXPAt(int level) {
        return levelMap.getOrDefault(level, -1);
    }

    public static int getLevelAtXP(int xp){
        for (Map.Entry<Integer, Integer> entry : levelMap.entrySet()) {
            if (entry.getValue() > xp) return entry.getKey() - 1;
        }
        return 1;
    }

    public static float getPercentToLevel(int xp){
        int currentLevel = getLevelAtXP(xp);
        if (currentLevel >= 99) return 1;

        int baseXP = getXPAt(currentLevel);
        int goalXP = getXPAt(currentLevel + 1) - baseXP;
        xp = xp - baseXP;

        return  (float) xp /  (float) goalXP;
    }

}
