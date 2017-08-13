package com.acuity.control.client.scripts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;

/**
 * Created by Zach on 8/12/2017.
 */
public class Downloader {

    public static File downloadIfNotPresent(File parentDir, String name, String downloadLink, boolean cleanDir) throws IOException {
        if (!parentDir.exists()) parentDir.mkdirs();

        File scriptFile = new File(parentDir, name);
        if (scriptFile.exists()) return scriptFile;

        File lockFile = new File(parentDir, "lock");
        lockFile.deleteOnExit();
        FileLock lock = new RandomAccessFile(lockFile, "rw").getChannel().lock();
        try {
            if (scriptFile.exists()) return scriptFile;

            if (downloadLink.endsWith("=0")) downloadLink = downloadLink.substring(0, downloadLink.length() - 1) + "1";
            if (!downloadLink.endsWith("?dl=1")) throw new IllegalArgumentException("Download link must end with '?dl=1'.");

            URL website = new URL(downloadLink);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(scriptFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            if (cleanDir){
                File[] files = parentDir.listFiles();
                if (files != null) Arrays.stream(files).filter(file -> !file.equals(scriptFile)).forEach(File::delete);
            }
            return scriptFile;
        }
        finally {
            lock.release();
            lockFile.delete();
        }
    }
}
