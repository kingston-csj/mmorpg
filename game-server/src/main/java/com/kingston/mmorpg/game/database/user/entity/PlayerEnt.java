package com.kingston.mmorpg.game.database.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "PlayerEnt")
@Proxy(lazy = false)
public class PlayerEnt {
	
	@Id
	@Column
	private long playerId;
	
	/**
	 * 所属账号id
	 */
	@Column
	private long accountId;
	
	@Column
	private int level;
	
	@Column
	private String data;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
