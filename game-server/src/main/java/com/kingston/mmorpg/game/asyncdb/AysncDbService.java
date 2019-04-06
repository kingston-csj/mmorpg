package com.kingston.mmorpg.game.asyncdb;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.kingston.mmorpg.common.util.BlockingUniqueQueue;
import com.kingston.mmorpg.common.util.thread.NamedThreadFactory;
import com.kingston.mmorpg.game.database.user.BaseEntity;
import com.kingston.mmorpg.game.database.user.CrudEntity;
import com.kingston.mmorpg.game.logs.LoggerUtils;

/**
 * 异步持久化服务
 */
@Component
public class AysncDbService {

	private BlockingQueue<CrudEntity> queue = new BlockingUniqueQueue<>();

	private final AtomicBoolean run = new AtomicBoolean(true);

	@PostConstruct
	private void init() {
		new NamedThreadFactory("db-save-service").newThread(new Worker()).start();
	}

	public void add2Queue(CrudEntity entity) {
		this.queue.add(entity);
	}

	private class Worker implements Runnable {
		@Override
		public void run() {
			while (run.get()) {
				CrudEntity gameEntity = null;
				try {
					gameEntity = queue.take();
					saveToDb(gameEntity.getEntity());
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
}
