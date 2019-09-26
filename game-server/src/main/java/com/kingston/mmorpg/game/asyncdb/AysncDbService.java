package com.kingston.mmorpg.game.asyncdb;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.kingston.mmorpg.common.util.BlockingUniqueQueue;
import com.kingston.mmorpg.common.util.thread.NamedThreadFactory;
import com.kingston.mmorpg.game.database.user.BaseEntity;
import com.kingston.mmorpg.game.logger.LoggerUtils;

/**
 * 异步持久化服务
 */
@Service
public class AysncDbService {

	private BlockingQueue<BaseEntity> queue = new BlockingUniqueQueue<>();

	private final AtomicBoolean run = new AtomicBoolean(true);

	@PostConstruct
	private void init() {
		new NamedThreadFactory("db-save-service").newThread(new Worker()).start();
	}

	public void add2Queue(BaseEntity entity) {
		this.queue.add(entity);
	}

	private class Worker implements Runnable {
		@Override
		public void run() {
			while (run.get()) {
				BaseEntity gameEntity = null;
				try {
					gameEntity = queue.take();
					saveToDb(gameEntity);
				} catch (Exception e) {
					LoggerUtils.error("", e);
					// 有可能是并发抛错，重新放入队列
					add2Queue(gameEntity);
				}
			}
		}
	}

	/**
	 * 数据真正持久化
	 * 
	 * @param entity
	 */
	@SuppressWarnings("unchecked")
	private void saveToDb(BaseEntity entity) {
		try {
			entity.getCrudRepository().save(entity);
		} catch (Exception e) {
			LoggerUtils.error("", e);
		}
	}

	@PreDestroy
	public void shutDown() {
		run.getAndSet(false);
		for (;;) {
			if (!queue.isEmpty()) {
				saveAllBeforeShutDown();
			} else {
				break;
			}
		}
		LoggerUtils.error("[db4Player] 执行全部命令后关闭");
	}

	private void saveAllBeforeShutDown() {
		while (!queue.isEmpty()) {
			Iterator<BaseEntity> it = queue.iterator();
			while (it.hasNext()) {
				BaseEntity ent = it.next();
				it.remove();
				saveToDb(ent);
			}
		}
	}
}
