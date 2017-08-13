package com.acuity.control.client.scripts;

import java.io.File;

/**
 * Created by Zach on 8/12/2017.
 */
public class AcuityDir {

    private static File home;
    private static File scripts;

    private static void init(){
        home = new File(System.getProperty("user.home"), "AcuityBotting");
        scripts = new File(home, "scripts");
        if (!home.exists()) home.mkdirs();
        if (!scripts.exists()) scripts.mkdirs();

    }

    static {
        init();
    }

    public static File getScripts() {
        return scripts;
    }

    public static File getHome() {
        return home;
    }
}
