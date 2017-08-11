package com.acuity.dropbox;

import com.acuity.security.DBAccess;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.google.common.base.Strings;
import com.google.common.io.Files;

import java.io.File;

/**
 * Created by Zachary Herridge on 8/10/2017.
 */
public class AcuityRepo {

    private static File workingDir;
    private static DbxClientV2 client;

    public static void init(){
        workingDir = new File(System.getProperty("java.io.tmpdir"));
        DbxRequestConfig config = DbxRequestConfig.newBuilder("AcuityRepo").withUserLocale("en_US").build();
        client = new DbxClientV2(config, DBAccess.getRepoKey());
    }

    public static File getWorkingDir() {
        return workingDir;
    }

    public static DbxClientV2 getClient() {
        return client;
    }

    public static void upload(Strings scriptKey){

    }

}
