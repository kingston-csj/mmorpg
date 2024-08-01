package org.forfun.mmorpg.game.id;

import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.entity.IdentityEntity;

import java.util.concurrent.atomic.AtomicLong;

public class IdService {

    public void init() {
        for (IdKey idKey : IdKey.values()) {
            IdentityEntity identityEntity = getEntityFromCache(idKey);
            if (identityEntity.getValue() == 0) {
                identityEntity.setValue(getInitSeed());
            }
            identityEntity.setFactory(new AtomicLong(identityEntity.getValue()));
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
        IdentityEntity identityEntity = getEntityFromCache(key);
        long nextId = identityEntity.getFactory().incrementAndGet();
        if (key.saveToDb) {
            GameContext.getAysncDbService().saveToDb(identityEntity);
        }
        return nextId;
    }

    private IdentityEntity getEntityFromCache(IdKey key) {
        return new IdentityEntity();
    }

}


