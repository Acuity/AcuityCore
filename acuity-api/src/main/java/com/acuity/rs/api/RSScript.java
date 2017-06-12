package com.acuity.rs.api;

import java.lang.String;

//Generated

public interface RSScript extends RSCacheableNode {

    int[] getInstructions();

    int[] getIntOperands();

    int getIntStackCount();

    int getLocalIntCount();

    int getLocalStringCount();

    String[] getStringOperands();

    int getStringStackCount();

    Object[] getSwitches();
}
