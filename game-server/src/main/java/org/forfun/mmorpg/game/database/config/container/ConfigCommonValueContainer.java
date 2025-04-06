package org.forfun.mmorpg.game.database.config.container;

import jforgame.data.Container;
import org.forfun.mmorpg.game.database.config.domain.ConfigCommonValue;

import java.util.HashMap;
import java.util.Map;

public class ConfigCommonValueContainer  extends Container<Integer, ConfigCommonValue> {

    private Map<String, ConfigCommonValue> map = new HashMap<>();

    @Override
    public void init() {
        data.forEach((k, v) -> {
            map.put(v.getKey(), v);
        });
    }

    public ConfigCommonValue getRecordByKey(String key) {
        return map.get(key);
    }
}
