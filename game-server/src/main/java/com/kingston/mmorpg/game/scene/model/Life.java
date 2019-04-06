package com.kingston.mmorpg.game.scene.model;

public class Life {

	private long currHp;

	private long maxHp;

	private long currMp;

	private long maxMp;

	public long reduceHp(long changeValue) {
		if (changeValue <= 0) {
			return this.currHp;
		}
		return this.currHp - changeValue;
	}

	public long getCurrHp() {
		return currHp;
	}

	public void setCurrHp(long currHp) {
		this.currHp = currHp;
	}

	public long getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(long maxHp) {
		this.maxHp = maxHp;
	}

	public long getCurrMp() {
		return currMp;
	}

	public void setCurrMp(long currMp) {
		this.currMp = currMp;
	}

	public long getMaxMp() {
		return maxMp;
	}

	public void setMaxMp(long maxMp) {
		this.maxMp = maxMp;
	}

}
