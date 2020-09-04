package com.kingston.mmorpg.game.script;

/**
 * 使用groovy编写的脚本
 * 用于一些不宜通过Agent热更的热点代码，（比如战斗）
 */
public interface IScript {

    String getId();
}
