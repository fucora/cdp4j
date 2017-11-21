package io.webfolder.cdp;

public class RemoteLauncherBuilder {
    private String host = "127.0.0.1";
    private int sshPort = 22;
    private int chromePort = 9222;
    private String user = "root";
    private String password = "";
    private String privateKey = "";
    private String chromeExecutable = "google-chrome";

    public RemoteLauncherBuilder withHost(String host) {
        if (host == null)
            throw new IllegalArgumentException();
        this.host = host;
        return this;
    }

    public RemoteLauncherBuilder withSshPort(int sshPort) {
        this.sshPort = sshPort;
        return this;
    }

    public RemoteLauncherBuilder withChromePort(int chromePort) {
        this.chromePort = chromePort;
        return this;
    }

    public RemoteLauncherBuilder withUser(String user) {
        if (user == null)
            throw new IllegalArgumentException();
        this.user = user;
        return this;
    }

    public RemoteLauncherBuilder withPassword(String password) {
        if (password == null)
            throw new IllegalArgumentException();
        this.password = password;
        return this;
    }


    public RemoteLauncherBuilder withPrivateKey(String privateKey) {
        if (privateKey == null)
            throw new IllegalArgumentException();
        this.privateKey = privateKey;
        return this;
    }

    public RemoteLauncherBuilder withChromeExecutable(String chromeExecutable) {
        if (chromeExecutable == null)
            throw new IllegalArgumentException();
        this.chromeExecutable = chromeExecutable;
        return this;
    }

    public RemoteLauncher create() {
        return new RemoteLauncher(host, sshPort,
                chromePort, user, password, privateKey, chromeExecutable);
    }
}
