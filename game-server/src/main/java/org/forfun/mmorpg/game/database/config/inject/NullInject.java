package org.forfun.mmorpg.game.database.config.inject;

public class NullInject implements ConfigValueParser {
    @Override
    public Object convert(String source) {
        return source;
    }
}
