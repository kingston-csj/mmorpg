package com.kingston.mmorpg.game;

public interface Modules {

	// ------------------底层功能支持模块（-128 ~ -1）-----------------
	int GM = -1;

	// ------------------业务功能模块（1~127）---------------------

	/** 玩家 */
	byte PLAYER = 1;
	/** 场景 */
	byte SCENE = 2;
	/** 活动 */
	byte ACTIVITY = 3;
	/** 技能 */
	byte SKILL = 4;

}
