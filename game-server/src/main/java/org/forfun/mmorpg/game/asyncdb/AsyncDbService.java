package org.forfun.mmorpg.game.asyncdb;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jforgame.commons.persist.DbService;
import jforgame.commons.persist.Entity;
import jforgame.commons.persist.PersistContainer;
import jforgame.commons.persist.QueueContainer;
import jforgame.commons.persist.SavingStrategy;
import org.forfun.mmorpg.game.ServerType;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.springframework.stereotype.Service;

/**
 * 异步持久化服务
 */
@Service
public class AsyncDbService implements DbService {


    private PersistContainer playerWorker;

    private PersistContainer commonWorker;

    @PostConstruct
    private void init() {
        SavingStrategy savingStrategy = new EntitySavingStrategy();
        playerWorker = new QueueContainer("playerDbContainer", savingStrategy);
        commonWorker = new QueueContainer("commonDbContainer", savingStrategy);
    }


    @Override
    public void saveToDb(Entity<?> entity) {
        if (GameContext.serverType != ServerType.GAME) {
            // only game server can saving data
            return;
        }
        if (entity instanceof PlayerEnt) {
            playerWorker.receive(entity);
        } else {
            commonWorker.receive(entity);
        }
    }

    @Override
    public void deleteFromDb(Entity<?> entity) {

    }

    @PreDestroy
    public void shutDown() {
        playerWorker.shutdownGraceful();
        commonWorker.shutdownGraceful();
    }

}
