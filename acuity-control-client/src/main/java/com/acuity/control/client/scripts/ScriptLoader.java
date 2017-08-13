package com.acuity.control.client.scripts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Zach on 8/12/2017.
 */
public class ScriptLoader {

    private static final Logger logger = LoggerFactory.getLogger(ScriptLoader.class);

    private static final String CLASS_EXTENSION = ".class";

    private JarFile jarFile;
    private ClassLoader loader;
    private Map<String, Class> loadedClasses = new HashMap<>();

    public void loadJar(File jarLocation) throws IOException {
        if (loadedClasses.size() > 0) {
            logger.info("{} already loaded.", jarLocation);
            return;
        }

        logger.info("Loading {}.", jarLocation);
        if (!jarLocation.exists()) throw new IllegalStateException("Missing script jar file.");

        jarFile = new JarFile(jarLocation);
        loader = URLClassLoader.newInstance(new URL[]{jarLocation.toURI().toURL()});

        final Enumeration entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) entries.nextElement();
            if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(CLASS_EXTENSION)) continue;

            String className = jarEntry.getName().substring(0, jarEntry.getName().length() - CLASS_EXTENSION.length());
            className = className.replace('/', '.');
            try {
                Class clazz = loader.loadClass(className);
                loadedClasses.put(className, clazz);
            } catch (ClassNotFoundException | NoClassDefFoundError e) {
                logger.trace("Failed to find class", e);
            }
            logger.debug("Loaded class {}.", className);
        }
        logger.debug("Loading {} complete.", jarLocation);
    }

    public Class getClass(Class type) throws IOException {
        return getClass(type.getName());
    }

    public Map<String, Class> getLoadedClasses() {
        return loadedClasses;
    }

    public Class getClass(String type) throws IOException {
        return loadedClasses.get(type);
    }


    public static void main(String[] args) {

        try {
            ScriptInstance scriptInstance = Scripts.loadScript("843170", "asdas", 5, "https://www.dropbox.com/s/82ob9romf488ca2/Script.jar?dl=1");
            scriptInstance.loadJar();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

   /*     try {
            ScriptLoader scriptLoader = new ScriptLoader();
            scriptLoader.loadJar(new File("C:\\Users\\Zach\\Dropbox\\Code\\Code Projects\\Topbot\\Old\\Private\\ProgWC\\out\\artifacts\\unnamed\\HProgWC.jar"));
            Class aClass = scriptLoader.getClass("com.horror.Woodcutting");
            System.out.println(aClass);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
