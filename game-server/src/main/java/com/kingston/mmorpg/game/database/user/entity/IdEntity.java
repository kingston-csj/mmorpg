package com.kingston.mmorpg.game.database.user.entity;

import com.kingston.mmorpg.game.database.user.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.CrudRepository;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class IdEntity implements BaseEntity<Integer> {

    private int key;

    private long value;

    private AtomicLong factory;

    @Override
    public Integer getId() {
        return key;
    }

    @Override
    public CrudRepository getCrudRepository() {
        return null;
    }
}
