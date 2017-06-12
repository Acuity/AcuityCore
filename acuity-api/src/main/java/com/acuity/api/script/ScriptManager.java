package com.acuity.api.script;

import com.acuity.api.script.impl.AcuityScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by MadDev on 6/11/17.
 */
public class ScriptManager {

    private static Logger logger = LoggerFactory.getLogger(ScriptManager.class);

    /**
     * The current script of the script manager
     * May be null if nothing is running.
     */
    private AcuityScript script;

    public AcuityScript getScript() {
        return script;
    }

    public void stopScript() {
        if (script == null)
            return;
        script.stop();
        script = null;
    }

    public void resumeScript() {
        if (script == null)
            return;
        script.resume();
    }

    public void pauseScript() {
        if (script == null)
            return;
        script.pause();
    }

    public void runScript(String path) {
        stopScript();
        final ScriptLoader loader = new ScriptLoader();
        AcuityScript script;
        try {
            script = loader.loadScriptFromJar(path);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("Failed to load script. {}", e.getMessage());
            return;
        }
        if (script == null) {
            logger.warn("Tried to load script, but not found. {}", path);
            return;
        }
        this.script = script;
        this.script.execute();
    }

    public void runScript(AcuityScript script) {
        if(script == null) {
            logger.warn("Tried to run a null script...");
            return;
        }
        stopScript();
        this.script = script;
        this.script.execute();
    }

}
