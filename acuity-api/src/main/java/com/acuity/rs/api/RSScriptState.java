package com.acuity.rs.api;

import java.lang.String;

//Generated

public interface RSScriptState {

    int getInvokedFromPc();

    RSScript getInvokedFromScript();

    int[] getSavedLocalInts();

    String[] getSavedLocalStrings();
}
