package com.neovisionaries.ws.client;

public class ZeroMasker implements Masker {

    public byte[] getMaskingKey() {
        return null;
    }

    public void mask(byte[] payload) {
        return;
    }
}
