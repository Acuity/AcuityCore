package com.acuity.control.client.scripts;

import com.acuity.db.domain.vertex.impl.scripts.Script;

import java.io.File;
import java.io.IOException;

/**
 * Created by Zach on 8/12/2017.
 */
public class Scripts {

    public static File downloadScript(String scriptKey, int scriptRev, String downloadLink) throws IOException {
        return Downloader.downloadIfNotPresent(new File(AcuityDir.getScripts(), scriptKey + "/jar/"), "script" + scriptRev + ".jar", downloadLink, true);
    }

    public static ScriptInstance loadScript(String key, String title, int rev, String jarURL) throws IOException {
        return new ScriptInstance(key, title, downloadScript(key, rev, jarURL));
    }

    public static ScriptInstance loadScript(Script script) throws IOException {
        return loadScript(script.getKey(), script.getTitle(), script.getScriptRev(), script.getJarURL());
    }
}
