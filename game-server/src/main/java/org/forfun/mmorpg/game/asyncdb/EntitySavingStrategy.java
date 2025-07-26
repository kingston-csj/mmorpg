package org.forfun.mmorpg.game.asyncdb;

import jforgame.commons.persist.Entity;
import jforgame.commons.persist.SavingStrategy;
import org.forfun.mmorpg.game.database.user.BaseEntity;

public class EntitySavingStrategy implements SavingStrategy {

    @Override
    public void doSave(Entity<?> entity) throws Exception {
        BaseEntity baseEntity = (BaseEntity) entity;
        if (baseEntity.isDelete()) {
            baseEntity.getCrudRepository().delete(entity);
        } else {
            baseEntity.getCrudRepository().save(entity);
        }
    }
}
