package org.forfun.mmorpg.game.asyncdb;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jforgame.commons.ds.ConcurrentHashSet;
import jforgame.commons.thread.NamedThreadFactory;
import org.forfun.mmorpg.game.ServerType;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 异步持久化服务
 */
@Service
public class AsyncDbService {


    private final AtomicBoolean run = new AtomicBoolean(true);

    private final Worker playerWorker = new Worker("player");

    private final Worker commonWorker = new Worker("common");

    @PostConstruct
    private void init() {
        new NamedThreadFactory("player-save-service").newThread(playerWorker).start();
        new NamedThreadFactory("common-save-service").newThread(commonWorker).start();
    }

    public void saveToDb(BaseEntity<? extends Serializable> entity) {
        if (GameContext.serverType != ServerType.GAME) {
            // only game server can saving data
            return;
        }
        if (entity instanceof PlayerEnt) {
            playerWorker.add2Queue(entity);
        } else {
            commonWorker.add2Queue(entity);
        }
    }


    private class Worker implements Runnable {

        private String name;

        private BlockingQueue<BaseEntity> queue = new LinkedBlockingDeque<>();

        private Set<String> savingQueue = new ConcurrentHashSet<>();

        public void add2Queue(BaseEntity entity) {
            String key = entity.getKey();
            if (savingQueue.contains(key)) {
                return;
            }
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
                if (entity.isDelete()) {
                    entity.getCrudRepository().delete(entity);
                } else {
                    entity.getCrudRepository().save(entity);
                }
                savingQueue.remove(entity.getKey());
            } catch (Exception e) {
                LoggerUtils.error("", e);
                // 重新放入队列
                add2Queue(entity);
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
