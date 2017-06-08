package com.acuity.rs.api;

//Generated

public interface RSIndexDataBase {

    int[] getArchiveCrcs();

    int[][] getArchiveFileIds();

    int[][] getArchiveFileNames();

    int[] getArchiveIds();

    int[] getArchiveNames();

    int[] getArchiveNumberOfFiles();

    int[] getArchiveRevisions();

    java.lang.Object[][] getBuffer();

    com.acuity.rs.api.RSIdentityTable[] getChildren();

    com.acuity.rs.api.RSIdentityTable getEntry();

    int getValidArchivesCount();

    byte invokeUnpack(int var0, int var1, int var2);
}
