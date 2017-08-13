package com.acuity.control.client.scripts;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Zach on 8/12/2017.
 */
public class ScriptInstance {

    private static final String CLASS_EXTENTION = ".class";

    private final String key;
    private final String title;
    private final File jarLocation;
    private ScriptLoader scriptLoader;

    public ScriptInstance(String key, String title, File jarLocation) {
        this.key = key;
        this.title = title;
        this.jarLocation = jarLocation;
        this.scriptLoader = new ScriptLoader();
    }

    public ScriptLoader getScriptLoader() {
        return scriptLoader;
    }

    public void loadJar() throws IOException {
        scriptLoader.loadJar(jarLocation);
    }
}
