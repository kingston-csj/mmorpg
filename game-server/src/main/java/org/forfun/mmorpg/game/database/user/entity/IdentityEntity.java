package org.forfun.mmorpg.game.database.user.entity;

import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.forfun.mmorpg.game.database.user.dao.IdentityDao;
import org.springframework.data.repository.CrudRepository;

import java.util.concurrent.atomic.AtomicLong;

public class IdentityEntity implements BaseEntity<Integer> {

    private int id;

    private long value;

    private AtomicLong factory;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public CrudRepository getCrudRepository() {
        return GameContext.getBean(IdentityDao.class);
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public AtomicLong getFactory() {
        return factory;
    }

    public void setFactory(AtomicLong factory) {
        this.factory = factory;
    }

}
