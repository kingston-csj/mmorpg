package org.forfun.mmorpg.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container<K, V> {

    private final Map<K, V> data = new HashMap<>();

    /**
     * index1_index2@id1_id2 --> List<V>
     */
    private final Map<String, List<V>> indexMapper = new HashMap<>();


    private final Map<String, IndexMeta> indexMetaMap = new HashMap<>();

    public void init(Class<?> clazz) {
        Arrays.stream(clazz.getDeclaredFields()).filter(f -> f.getAnnotation(Index.class) != null)
                .forEach(f -> {
                    IndexMeta indexMeta = new FieldIndexMeta(f);
                    String key = indexMeta.getName();
                    if (indexMetaMap.put(key, indexMeta) != null) {
                        throw new RuntimeException(String.format("%s类索引重复-->%s", clazz.getName(), key));
                    }
                    indexMetaMap.put(key, indexMeta);
                });

        Arrays.stream(clazz.getDeclaredMethods()).filter(m -> m.getAnnotation(Index.class) != null)
                .forEach(m -> {
                    IndexMeta indexMeta = new MethodIndexMeta(m);
                    String key = indexMeta.getName();
                    if (indexMetaMap.put(key, indexMeta) != null) {
                        throw new RuntimeException(String.format("%s类索引重复-->%s", clazz.getName(), key));
                    }
                    indexMetaMap.put(key, indexMeta);
                });
    }

}
