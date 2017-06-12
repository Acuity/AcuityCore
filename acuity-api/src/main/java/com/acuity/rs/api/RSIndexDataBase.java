package com.acuity.rs.api;

import java.lang.Object;

//Generated

public interface RSIndexDataBase {

    int[] getArchiveCrcs();

    int[][] getArchiveFileIds();

    int[][] getArchiveFileNames();

    int[] getArchiveIds();

    int[] getArchiveNames();

    int[] getArchiveNumberOfFiles();

    int[] getArchiveRevisions();

    Object[][] getBuffer();

    RSIdentityTable[] getChildren();

    RSIdentityTable getEntry();

    int getValidArchivesCount();

    byte invokeUnpack(int var0, int var1, int var2);
}
