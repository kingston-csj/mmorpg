package org.forfun.mmorpg.game.script;

import groovy.lang.GroovyClassLoader;
import jakarta.annotation.PostConstruct;
import jforgame.commons.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ScriptService {

    private ConcurrentMap<String, IScript> scripts = new ConcurrentHashMap<>();

    private GroovyClassLoader loader = new GroovyClassLoader(ScriptService.class.getClassLoader());

    private Logger logger = LoggerFactory.getLogger(ScriptService.class);

    @PostConstruct
    private void init() throws Exception {
        Resource fileResource = new ClassPathResource("groovy");
        // 优先resource下的脚本文件
        for (File file : fileResource.getFile().listFiles()) {
            String code = FileUtils.readFullText(file.getAbsolutePath());
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            Class groovyClass = loader.parseClass(code, fileName);
            IScript groovyObject = (IScript) groovyClass.newInstance();
            scripts.put(groovyObject.getId(), groovyObject);
        }
        // resource没有的，则创建一个默认的，避免业务代码进行判空 TODO

        logger.error("加载groovy脚本数量为 " + scripts.size());
    }

    public <T extends IScript> T getScript(Class<T> script) {
        return (T) scripts.get(script.getSimpleName());
    }

}
