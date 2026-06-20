package org.forfun.mmorpg.game.id;

import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.entity.IdentityEnt;

import java.util.concurrent.atomic.AtomicLong;

public class IdService {

    public void init() {
        for (IdKey idKey : IdKey.values()) {
            IdentityEnt identityEnt = getEntityFromCache(idKey);
            if (identityEnt.getValue() == 0) {
                identityEnt.setValue(getInitSeed());
            }
            identityEnt.setFactory(new AtomicLong(identityEnt.getValue()));
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
        IdentityEnt identityEnt = getEntityFromCache(key);
        long nextId = identityEnt.getFactory().incrementAndGet();
        if (key.saveToDb) {
            GameContext.getAysncDbService().saveToDb(identityEnt);
        }
        return nextId;
    }

    private IdentityEnt getEntityFromCache(IdKey key) {
        return new IdentityEnt();
    }

}


