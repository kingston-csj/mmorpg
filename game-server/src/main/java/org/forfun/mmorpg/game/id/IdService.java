package org.forfun.mmorpg.game.id;

import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.entity.IdEntity;

import java.util.concurrent.atomic.AtomicLong;

public class IdService {

    public void init() {
        for (IdKey idKey : IdKey.values()) {
            IdEntity idEntity = getEntityFromCache(idKey);
            if (idEntity == null) {
                idEntity.setValue(getInitSeed());
            }
            idEntity.setFactory(new AtomicLong(idEntity.getValue()));
        }
    }

    private long getInitSeed() {
        //----------------id格式 -------------------------
        //----------long类型8个字节64个比特位----------------
        // 高16位          	| 低48位
        // serverId           自增长号
        long serverId = GameContext.getServerConfig().getServerId();
        return serverId << 48;
    }

    public long getNextKey(IdKey key) {
        // 获取缓存里的cache
        IdEntity idEntity = getEntityFromCache(key);
        long nextId = idEntity.getFactory().incrementAndGet();
        if (key.saveToDb) {
            GameContext.getAysncDbService().saveToDb(idEntity);
        }
        return nextId;
    }

    private IdEntity getEntityFromCache(IdKey key) {
        return new IdEntity();
    }

}


