package org.forfun.mmorpg.tools;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import jakarta.persistence.Id;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ConfigFileExport {

    private static String JAVA_FILE_DIR = "XXX";

    /**
     * 模版目录的本地绝对路径
     */
    private static String TEMPLATE_FILE_DIR = "/Users/xxyy/projects/mmorpg/game-server/template/";

    public static void main(String[] args) throws Exception {
        String configDataName = "ConfigSkill";
        Class<?> c = Class.forName("org.forfun.mmorpg.game.database.config.domain." + configDataName);
        String idType = "Integer";
        for (Field field : c.getDeclaredFields()) {
            Annotation aa = field.getAnnotation(Id.class);
            if (aa != null) {
                // 不是基本类型，直接取
                if (!field.getType().isPrimitive()) {
                    idType = field.getType().getSimpleName();
                } else {
                    if (field.getType() == int.class) {
                        idType = "Integer";
                    }
                }
            }
        }

        Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_DIR));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);

        Map<String, String> data = new HashMap<>();
        data.put("config", configDataName);
        String tableName = lowerFirstLetter(configDataName.replace("Config", ""));
        data.put("daoName", tableName);
        data.put("dataName", tableName + "s");
        data.put("idType", idType);

        templateToFile(cfg, "ConfigContainer.ftl", data, TEMPLATE_FILE_DIR + configDataName + "Container.java");
        templateToFile(cfg, "ConfigDao.ftl", data, TEMPLATE_FILE_DIR + configDataName + "Dao.java");
    }

    private static void templateToFile(Configuration cfg, String template, Map<String, String> data, String targetName)
            throws Exception {
        Writer out = new OutputStreamWriter(new FileOutputStream(targetName), StandardCharsets.UTF_8);
        Template temp = cfg.getTemplate(template);
        temp.process(data, out);
        out.flush();
    }

    private static String lowerFirstLetter(String content) {
        String firstLetter = content.substring(0, 1);
        return firstLetter.toLowerCase() + content.substring(1);
    }
}
