package io.webfolder.cdp.type.target;

import com.vimeo.stag.UseStag;

import io.webfolder.cdp.annotation.Experimental;

@Experimental
@UseStag
public class RemoteLocation {
    private String host;

    private Integer port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
