package com.kingston.mmorpg.game.database.user.entity;

import com.kingston.mmorpg.game.asyncdb.DelayPersistence;
import com.kingston.mmorpg.game.base.GameContext;
import com.kingston.mmorpg.game.database.converter.JpaObjectConverter;
import com.kingston.mmorpg.game.database.user.BaseEntity;
import com.kingston.mmorpg.game.database.user.dao.PlayerDao;
import com.kingston.mmorpg.game.scene.actor.ActorType;
import com.kingston.mmorpg.game.scene.actor.Creature;
import com.kingston.mmorpg.game.vip.model.VipRight;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PlayerEnt")
@Proxy(lazy = false)
@Getter
@Setter
public class PlayerEnt extends Creature implements BaseEntity<Long>, DelayPersistence {

    @Id
    @Column
    private long playerId;

    /**
     * 所属账号id
     */
    @Column
    private long accountId;

    @Column
    private String name;

    @Column
    private int level;

    @Column
    @Convert(converter = JpaObjectConverter.class)
    private VipRight vipRight;

    @Override
    public CrudRepository<PlayerEnt, Long> getCrudRepository() {
        return GameContext.getBean(PlayerDao.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long getId() {
        return playerId;
    }

    public PlayerEnt() {

    }

    @Override
    public ActorType getType() {
        return ActorType.Player;
    }

    public boolean isOpenedFunction(int funcId) {
        return false;
    }

}
