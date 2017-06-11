package com.acuity.rs.api;

//Generated

import java.io.RandomAccessFile;

public interface RSFileOnDisk {

    RandomAccessFile getFile();

    long getLength();

    long getPosition();
}
