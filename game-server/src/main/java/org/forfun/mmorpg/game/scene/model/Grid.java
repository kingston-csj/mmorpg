package org.forfun.mmorpg.game.scene.model;

/**
 * 场景移动划分的格子
 * 
 *
 */
public class Grid {

	/** 宽度为25像素 */
	public static final int WIDTH = 25;
	/** 掩码 */
	private byte code;

	private final static byte WALK = 1;
	private final static byte SAFE = 1 << 1;
	private final static byte BLOCK = 1 << 2;

	public boolean canWalk() {
		return (code & WALK) > 0;
	}

	public boolean isSafe() {
		return (code & SAFE) > 0;
	}

	public void openWalk() {
		this.code |= WALK;
	}

	public void openBlock() {
		this.code |= BLOCK;
	}

}
