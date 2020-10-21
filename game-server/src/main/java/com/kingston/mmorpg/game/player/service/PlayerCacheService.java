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

@Log
@Service
public class PlayerCacheService  {

    @Autowired
    private PlayerDao playerDao;

    @Cacheable(cacheNames = "player")
    public Player getPlayer(long id) {
        log.info("查询角色 " + id);
        PlayerEnt playerEnt = playerDao.getOne(id);
        if (playerEnt != null) {
            Player player = new Player();
            player.setId(id);
            player.setPlayerEnt(playerEnt);
            return player;
        } else {
            return null;
        }
    }

    /**
     * 保存玩家数据
     *
     * @param player
     */
    @CachePut(cacheNames = "player")
    public void savePlayer(Player player) {
        SpringContext.getAysncDbService().add2Queue(player.getEntity());
    }
}
