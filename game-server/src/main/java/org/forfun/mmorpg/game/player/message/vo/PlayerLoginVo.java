package org.forfun.mmorpg.game.player.message.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerLoginVo {

	private long id;
	private String name;
	/** 角色战力 */
	private long fighting;

}
