package org.forfun.mmorpg.game.database.user.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Transient;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.battle.model.BattleContext;
import org.forfun.mmorpg.game.database.converter.JpaObjectConverter;
import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.forfun.mmorpg.game.database.user.dao.PlayerDao;
import org.forfun.mmorpg.game.scene.actor.ActorType;
import org.forfun.mmorpg.game.scene.actor.Creature;
import org.forfun.mmorpg.game.vip.model.VipRight;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.data.repository.CrudRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "playerent")
@Proxy(lazy = false)
@Getter
@Setter
public class PlayerEnt extends Creature implements BaseEntity<Long> {

    @Id
    @Column
    private long id;

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

    @Transient
    private BattleContext battleContext;

    @Override
    public CrudRepository<PlayerEnt, Long> getCrudRepository() {
        return GameContext.getBean(PlayerDao.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Long getId() {
        return id;
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
