package io.webfolder.cdp.session;

public class SessionSettings {

    private static final Integer DEFAULT_SCREEN_WIDTH = 1366; // WXGA width

    private static final Integer DEFAULT_SCREEN_HEIGHT = 768; // WXGA height

    private final int screenWidth;

    private int screenHeight;

    public SessionSettings() {
        this(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
    }

    public SessionSettings(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    @Override
    public String toString() {
        return "SessionSettings [screenWidth=" + screenWidth + ", screenHeight=" + screenHeight + "]";
    }
}
