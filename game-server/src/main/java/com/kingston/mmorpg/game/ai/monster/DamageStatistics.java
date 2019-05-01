package com.kingston.mmorpg.game.ai.monster;

import java.util.HashMap;
import java.util.Map;

/**
 * 怪物的伤害统计
 * 
 * @author kingston
 *
 */
public class DamageStatistics {
	
	/**
	 * 统计列表 攻击方id -> 伤害值
	 */
	public Map<Long, Long> damages = new HashMap<>();
	
	public void addDamage(long attacker, long hurt) {
		this.addDamage(attacker, hurt);
	}
	
	public long getDamage(long attacker) {
		return damages.getOrDefault(attacker, 0L);
	}
	
	public void clearDamage() {
		this.damages.clear();
	}

}
