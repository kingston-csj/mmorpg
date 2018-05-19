package com.kingston.mmorpg.game.database.config.bean;

/**
 * 地图资源
 * @author kingston
 *
 */
public class ConfigMap {
	
	private int id;
	
	private String name;
	
	private int width;
	
	private int height;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
