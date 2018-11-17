package com.kingston.mmorpg.game.scene.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.kingston.mmorpg.game.database.config.domain.ConfigMap;
import com.kingston.mmorpg.game.scene.actor.GameObject;

public class Scene extends GameObject {
	
	private static AtomicLong idFactory = new AtomicLong();
	
	/** 母地图 */
	private WorldMap parent;
	
	/**
	 * 场景分线号
	 */
	private int lineId;
	
	/** 场景分屏 */
	private Map<Integer, Area> areas = new HashMap<>();
	
	/** 当前场景的动态阻挡 */
	private Set<Integer> dynamicBlocks = new HashSet<>();
	
	public Scene() {
		this.id = idFactory.getAndIncrement();
	}
	
	
	public void init() {
		ConfigMap configMap = new ConfigMap();
		// 根据地图长宽进行分屏
		int width  = configMap.getWidth();
		int height = configMap.getHeight();
		
		int widthRange = width % Area.WIDTH == 0 ? width / Area.WIDTH : width / Area.WIDTH + 1;
		int heightRange = height % Area.HEIGHT == 0 ? height / Area.HEIGHT : height / Area.HEIGHT + 1;
		// TODO 改为延迟初始化
		for (int i = 0; i < widthRange; i++) {
			for (int j = 0; j < heightRange; j++) {
				int index = i*1000+j;
				Area area = new Area(index);
				areas.put(index, area);
			}
		}
	}
	
	public void openDynamicBlocks(int blockId) {
		this.dynamicBlocks.add(blockId);
	}
	
	public void closeDynamicBlocks(int blockId) {
		this.dynamicBlocks.remove(blockId);
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
}
