package org.forfun.mmorpg.game;

public interface Modules {

	// ------------------底层功能支持模块（-128 ~ -1）-----------------
	short BASE = -1;
	short GM = -2;
	short CROSS = -3;

	// ------------------业务功能模块（1~326）---------------------

	/** 玩家 */
	short PLAYER = 1;
	/** 场景 */
	short SCENE = 2;
	/** 活动 */
	short ACTIVITY = 3;
	/** 技能 */
	short SKILL = 4;

}
