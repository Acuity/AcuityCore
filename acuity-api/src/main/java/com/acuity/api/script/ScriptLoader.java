package com.acuity.api.script;

import com.acuity.api.script.impl.AcuityScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by MadDev on 6/11/17.
 */
public class ScriptLoader {

    private static Logger logger = LoggerFactory.getLogger(ScriptLoader.class);

    /**
     * @param path, the path to the Jar file.
     * @return An AcuityScript instance of the jar file, if it contains a class extending AcuityScript.
     */
    AcuityScript loadScriptFromJar(String path)
            throws Exception {

        final File file = new File(path);
        final JarFile jar = new JarFile(file);
        final Enumeration entries = jar.entries();

        while (entries.hasMoreElements()) {

            final JarEntry entry = (JarEntry) entries.nextElement();
            String name = entry.getName();

            final ClassLoader loader = URLClassLoader.newInstance(
                    new URL[]{file.toURI().toURL()},
                    getClass().getClassLoader()
            );
            if (!name.endsWith(".class")) {
                continue;
            }
            name = name.replace("/", ".").replace(".class", "");
            try {

                final Class<?> clazz = Class.forName(name, true, loader);
                final Class<? extends AcuityScript> runClass = clazz.asSubclass(AcuityScript.class);
                final Constructor<? extends AcuityScript> constructor = runClass.getConstructor();
                logger.info("Found AcuityScript at " + name);
                return constructor.newInstance();

            } catch (ClassCastException e) {
                //if this is thrown, then the class is loaded
                //does not extend acuity script, so we cannot run it.
                //ignore
            }
        }

        logger.warn("No scripts found.");
        return null;
    }
}
