package com.kingston.mmorpg.game.database.config.container;

import com.kingston.mmorpg.game.base.GameContext;
import com.kingston.mmorpg.game.database.config.dao.ConfigCommonValueDao;
import com.kingston.mmorpg.game.database.config.domain.ConfigCommonValue;
import com.kingston.mmorpg.game.database.config.inject.CommonValueAutoInjectHandler;
import com.kingston.mmorpg.game.database.config.inject.CommonValueReloadListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConfigCommonValueContainer implements ReloadableContainer<String, ConfigCommonValue> {

    @Autowired
    private ConfigCommonValueDao dao;

    private Map<String, ConfigCommonValue> data = new HashMap<>();

    @Override
    public void reload() {
        data = dao.findAll().stream().collect(Collectors.toMap(ConfigCommonValue::getId, Function.identity()));

        // 为service注入CommonValue的值
        CommonValueAutoInjectHandler autoInjectHandler = GameContext.getBean(CommonValueAutoInjectHandler.class);
        Map<String, Object> services = GameContext.getBeansWithAnnotation(Service.class);
        for (Map.Entry<String, Object> entry : services.entrySet()) {
            autoInjectHandler.postBeanAfterInject(entry.getValue());
        }

        // 对 CommonValueReloadListener实现类进行reload
        GameContext.getBeansOfType(CommonValueReloadListener.class).forEach(CommonValueReloadListener :: afterReload);
    }

    @Override
    public ConfigCommonValue queryOne(String id) {
        return data.get(id);
    }

    @Override
    public Collection<ConfigCommonValue> queryAll() {
        return data.values();
    }
}
