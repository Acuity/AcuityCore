package com.acuity.rs.api;


//Generated

public interface RSBuffer extends RSNode {

    int getOffset();

    byte[] getPayload();

    void invokeDecryptXtea(int var0);

    void invokeEncryptXtea(int var0, int var1, int var2);

    void invokeEncryptXtea2(int var0);

    void invokePut24bitInt(int var0);

    void invokePutByte(int var0);

    void invokePutBytes(byte var0, int var1, int var2);

    void invokePutInt(int var0);

    void invokePutLong(long var0);

    void invokePutShort(int var0);

    void invokePutVarInt(int var0);

    int invokeRead24BitInt();

    void invokeReadBytes(byte var0, int var1, int var2);

    int invokeReadInt();

    long invokeReadLong();

    int invokeReadShort();

    int invokeReadShortSmart();

    String invokeReadString();

    int invokeReadUnsignedByte();

    int invokeReadUnsignedShort();

    int invokeReadVarInt();
}
