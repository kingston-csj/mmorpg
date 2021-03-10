package org.forfun.mmorpg.game.database.user.entity;

import org.forfun.mmorpg.game.database.user.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.CrudRepository;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class IdEntity implements BaseEntity<Integer> {

    private int id;

    private long value;

    private AtomicLong factory;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public CrudRepository getCrudRepository() {
        return null;
    }

}
