package com.kingston.mmorpg.game.scene.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author kingston 世界地图模型
 */
public class WorldMap {

	/** 以该地图为模型的所有场景 */
	private ConcurrentMap<Long, Scene> scenes = new ConcurrentHashMap<>();

	/** 地图模型id */
	private int modelId;
	
	/** 读写锁 */
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
	private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
	
	public void addScene(long id, Scene scene) {
		scenes.put(id, scene);
	}
	
	
}
