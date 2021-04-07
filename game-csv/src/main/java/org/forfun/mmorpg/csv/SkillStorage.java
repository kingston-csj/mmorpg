package org.forfun.mmorpg.csv;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class SkillStorage<T> {

    private List<T> data;

    private Set<Class<?>> baseTypes;

  {
        baseTypes = new HashSet<>();
        baseTypes.add(String.class);
    }


    public void read(T prototype, String[] header, List<String[]> records) {
        data = new ArrayList<>(records.size());
        Class<T> clazz = (Class<T>) prototype;
        ConversionService conversionService = Context.getConversionService();
        try {
            for (int i = 0; i < records.size(); i++) {
                String[] record = records.get(i);

                T obj = clazz.newInstance();
                for (int j = 0; j < header.length; j++) {
                    String colName = header[j];
                    if (StringUtils.isEmpty(colName)) {
                        continue;
                    }

                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();
                    Object fieldVal = null;
                    if (fieldType.isArray()) {
                        record[j] = record[j].substring(1, record[j].length() - 1);
                    }
//                    if (fieldType.isPrimitive() || baseTypes.contains(fieldType)) {
                        fieldVal = (T) conversionService.convert(record[j], field.getType());
//                    } else {
//                        //统计用json解析
//                    }
                    field.set(obj,fieldVal);
                }
                data.add(obj);

            }

            System.out.println(data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
