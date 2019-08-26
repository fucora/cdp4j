package io.webfolder.cdp.session;

import java.io.InputStream;
import java.io.OutputStream;

public class PipeConnection implements Connection {

    private final InputStream is;

    private final OutputStream os;

    public PipeConnection(InputStream is, OutputStream os) {
        this.is = is;
        this.os = os;
    }

    InputStream getInput() {
        return is;
    }

    OutputStream getOutput() {
        return os;
    }
}
