package com.kingston.mmorpg.game.player.service;

import com.kingston.mmorpg.game.base.EntityCacheService;
import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.database.user.BaseEntity;
import com.kingston.mmorpg.game.database.user.dao.PlayerDao;
import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;
import com.kingston.mmorpg.game.scene.actor.Player;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Log
@Service
public class PlayerCacheService implements EntityCacheService<PlayerEnt, Long> {

    @Autowired
    private PlayerDao playerDao;

    @Override
    @Cacheable(cacheNames = "player")
    public PlayerEnt getEntity(Long id, Class clazz) {
        log.info("查询角色 " + id);
        PlayerEnt playerEnt = playerDao.getOne(id);
        if (playerEnt != null) {
            Player player = new Player();
            player.setId(id);
            player.setPlayerEnt(playerEnt);
            playerEnt.setPlayer(player);
            return playerEnt;
        } else {
            return null;
        }
    }

    /**
     * 保存玩家数据
     *
     * @param playerEnt
     */
    @Override
    @CachePut(cacheNames = "player")
    public PlayerEnt putEntity(PlayerEnt playerEnt) {
        return null;
    }
}
