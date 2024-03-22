package org.forfun.mmorpg.game.database.config.inject;


import jforgame.commons.NumberUtil;

public class IntArrayConfigValueParser implements ConfigValueParser<int[]> {

    @Override
    public int[] convert(String source) {
        String[] splits = source.split("_");
        int[] result = new int[splits.length];
        for (int i = 0; i < splits.length; i++) {
            result[i] = NumberUtil.intValue(splits[i]);
        }
        return result;
    }
}
