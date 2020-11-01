package com.kingston.mmorpg.game.asyncdb;

import com.kingston.mmorpg.common.util.BlockingUniqueQueue;
import com.kingston.mmorpg.common.util.thread.NamedThreadFactory;
import com.kingston.mmorpg.game.database.user.BaseEntity;
import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;
import com.kingston.mmorpg.game.logger.LoggerUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 异步持久化服务
 */
@Service
public class AsyncDbService {


    private final AtomicBoolean run = new AtomicBoolean(true);

    private Worker playerWorker = new Worker("player");

    private Worker commonWorker = new Worker("common");


    @PostConstruct
    private void init() {
        new NamedThreadFactory("player-save-service").newThread(playerWorker).start();
        new NamedThreadFactory("common-save-service").newThread(commonWorker).start();
    }

	public void saveToDb(BaseEntity entity) {
		if (entity instanceof PlayerEnt) {
			playerWorker.add2Queue(entity);
		} else {
			commonWorker.add2Queue(entity);
		}
	}


    private class Worker implements Runnable {

        private String name;

        private BlockingQueue<BaseEntity> queue = new BlockingUniqueQueue<>();

        public void add2Queue(BaseEntity entity) {
            this.queue.add(entity);
        }

        Worker(String name) {
            this.name = name;
        }

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

		public void shutDown() {
			for (; ; ) {
				if (!queue.isEmpty()) {
					saveAllBeforeShutDown();
				} else {
					break;
				}
			}
			LoggerUtils.error("[" + name + "持久器] 执行全部命令后关闭");
		}
    }

    @PreDestroy
    public void shutDown() {
        run.getAndSet(false);
        playerWorker.shutDown();
        commonWorker.shutDown();
    }

}
