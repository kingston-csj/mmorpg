package org.forfun.mmorpg.game.database.user.entity;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.forfun.mmorpg.game.database.user.dao.IdentityDao;
import org.springframework.data.repository.CrudRepository;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
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

}
