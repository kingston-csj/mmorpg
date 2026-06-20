package org.forfun.mmorpg.game.database.config.domain;

import jakarta.persistence.Id;

/**
 * 地图资源
 *
 */
public class ConfigMap {

    @Id
    private int id;

    /**
     * 地图类型 0为普通场景，1为副本场景
     */
    private byte mapType;

    private String name;

    private int width;

    private int height;

    /**
     * 基础分线数量
     */
    private int coreLine;

    /**
     * 最大分线数量
     */
    private int maxLine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getMapType() {
        return mapType;
    }

    public void setMapType(byte mapType) {
        this.mapType = mapType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCoreLine() {
        return coreLine;
    }

    public void setCoreLine(int coreLine) {
        this.coreLine = coreLine;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

}
