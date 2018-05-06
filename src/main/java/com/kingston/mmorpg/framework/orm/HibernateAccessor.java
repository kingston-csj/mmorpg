package com.kingston.mmorpg.framework.orm;

import java.io.Serializable;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class HibernateAccessor extends HibernateDaoSupport implements Accessor {

	@Override
	public <PK extends Serializable, T extends Entity> T load(Class<T> clazz, PK id) {
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public <PK extends Serializable, T extends Entity> PK save(Class<T> clazz, T entity) {
		return (PK)getHibernateTemplate().save(entity);
	}

}
