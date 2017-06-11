package com.acuity.rs.api;

//Generated

public interface RSPacketBuffer extends RSBuffer {

    RSISAACCipher getCipher();

    int invokeReadOpcode();

    void invokeSeed(int var0);

    void invokeWriteHeader(int var0);
}
