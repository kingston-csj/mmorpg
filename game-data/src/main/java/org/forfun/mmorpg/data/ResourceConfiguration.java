package org.forfun.mmorpg.data;

public class ResourceConfiguration {

    /**
     * 资源根目录
     */
    private String location;

    /**
     * 文件后缀
     */
    private String suffix;

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
}
