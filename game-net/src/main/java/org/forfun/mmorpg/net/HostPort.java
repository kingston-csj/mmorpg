package org.forfun.mmorpg.net;

public class HostPort {

    /**
     * ip address
     */
    private String host;

    /**
     * socket port
     */
    private int port;

    public static HostPort valueOf(String host, int port) {
        HostPort hostPort = new HostPort();
        hostPort.host = host;
        hostPort.port = port;

        return hostPort;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
