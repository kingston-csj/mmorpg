package com.kingston.mmorpg.framework.net.socket.task;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DispatchComputer {
	
	private static AtomicInteger id = new AtomicInteger();
	
	private static ConcurrentMap<Long, Integer> key2Id = new ConcurrentHashMap<>();
	
	public static int getWorker(int mapId, int lineId) {
		long key = createKey(mapId, lineId);
		if (key2Id.containsKey(key)) {
			return key2Id.get(key);
		}
		int nextKey = id.incrementAndGet();  
		key2Id.putIfAbsent(key, nextKey);
		return key2Id.get(key);
	}
	
	public static long createKey(int mapId, int lineId) {
		return lineId * 10000000 + mapId;
	}

}
