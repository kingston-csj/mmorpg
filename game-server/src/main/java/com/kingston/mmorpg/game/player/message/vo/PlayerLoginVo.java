package com.kingston.mmorpg.game.player.message.vo;

import lombok.Data;

@Data
public class PlayerLoginVo {

	private long id;
	private String name;
	/** 角色战力 */
	private long fighting;

}
