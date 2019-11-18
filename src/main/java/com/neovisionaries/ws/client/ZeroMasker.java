package com.neovisionaries.ws.client;

public class ZeroMasker implements Masker {

    private final byte[] maskingKey = new byte[] {0x0,0x0,0x0,0x0};

    public byte[] getMaskingKey() {
        return maskingKey;
    }

    public void mask(byte[] payload) {
        return;
    }
}
