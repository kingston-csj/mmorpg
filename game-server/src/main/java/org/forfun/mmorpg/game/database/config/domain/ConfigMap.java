package org.forfun.mmorpg.game.database.config.domain;

import jakarta.persistence.Id;
import lombok.Getter;

/**
 * 地图资源
 *
 */
@Getter
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

}
