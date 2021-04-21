package org.forfun.mmorpg.framework.net;

import lombok.Data;

@Data
public class NodeConfig {

    private String ip;

    private int port;

    public static NodeConfig valueOf(String ip, int port) {
        NodeConfig config = new NodeConfig();
        config.ip   = ip;
        config.port = port;
        return config;
    }
}
