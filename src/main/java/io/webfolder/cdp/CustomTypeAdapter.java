package io.webfolder.cdp;

public enum CustomTypeAdapter {
    /**
     * Use reflection based gson type adapter
     */
    Reflection,
    /**
     * Use stag generated gson type adapter
     */
    Generated
}
