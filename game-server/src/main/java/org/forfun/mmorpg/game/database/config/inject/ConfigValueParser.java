package org.forfun.mmorpg.game.database.config.inject;

public interface ConfigValueParser<T> {

    T convert(String source);

}
