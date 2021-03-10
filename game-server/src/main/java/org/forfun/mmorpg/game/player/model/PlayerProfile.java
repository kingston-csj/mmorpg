package org.forfun.mmorpg.game.player.model;

public class PlayerProfile {

	private long playerId;

	private long accountId;

	private String name;

	private int level;

	public PlayerProfile(long playerId, long accountId, String name, int level) {
		super();
		this.playerId = playerId;
		this.accountId = accountId;
		this.name = name;
		this.level = level;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long id) {
		this.playerId = id;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "PlayerBaseInfo [id=" + playerId + ", accountId=" + accountId + ", name=" + name + ", level=" + level
				+ "]";
	}

}
