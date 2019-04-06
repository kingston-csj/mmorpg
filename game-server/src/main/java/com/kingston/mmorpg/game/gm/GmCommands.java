package com.kingston.mmorpg.game.gm;

/**
 * gm命令枚举 这里的枚举是为了方便看到项目的所有gm命令，便于用户查看 但该类只做方法签名，具体的逻辑放在对应模块的facade层
 * 这样是因为：后期随着gm命令增加，业务gm代码非常庞大，很容易超过5K行，所以不宜统一放在一个类
 * 
 * @author kingston
 *
 */
public enum GmCommands {

	/**
	 * 设置玩家等级 [level]
	 */
	LEVEL,

}
