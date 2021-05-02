package org.forfun.mmorpg.data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "game.data",ignoreInvalidFields=true)
public class ResourceProperties {

    /**
     * 资源根目录
     */
    private String location="resource";

    /**
     * 文件后缀(默认)
     */
    private String suffix = ".csv";

    private String scanPath = "org.forfun.mmorpg";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getScanPath() {
        return scanPath;
    }

    public void setScanPath(String scanPath) {
        this.scanPath = scanPath;
    }
}
