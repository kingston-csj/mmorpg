package com.kingston.mmorpg.game.scene.model;

public final class Position {

	/** 横坐标(单位为像素) */
	private final int x;
	/** 纵坐标(单位为像素) */
	private final int y;

	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
