package com.acuity.rs.api;

import java.io.OutputStream;
import java.net.Socket;
import java.io.InputStream;

//Generated

public interface RSSocket extends Runnable {

    InputStream getInputStream();

    byte[] getOutbuffer();

    int getOutbufLen();

    OutputStream getOutputStream();

    Socket getSocket();

    int invokeAvailable();

    void invokeFinalize();

    void invokeQueueForWrite(byte var0, int var1, int var2);

    void invokeRead(byte var0, int var1, int var2);

    int invokeReadByte();
}
