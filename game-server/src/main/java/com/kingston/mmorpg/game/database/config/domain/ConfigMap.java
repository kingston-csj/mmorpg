package com.kingston.mmorpg.game.database.config.domain;

/**
 * 地图资源
 * 
 * @author kingston
 *
 */
public class ConfigMap {

	private int id;

	/**
	 * 地图类型 0为普通场景，1为副本场景
	 */
	private byte mapType;

	private String name;

	private int width;

	private int height;
	
	/**
	 * 基础分线数量
	 */
	private int coreLine;
	
	/**
	 * 最大分线数量
	 */
	private int maxLine;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public byte getMapType() {
		return mapType;
	}

	public int getCoreLine() {
		return coreLine;
	}

	public void setCoreLine(int coreLine) {
		this.coreLine = coreLine;
	}

	public int getMaxLine() {
		return maxLine;
	}

	public void setMaxLine(int maxLine) {
		this.maxLine = maxLine;
	}
	
	

}
