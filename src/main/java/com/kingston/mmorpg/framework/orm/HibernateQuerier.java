package com.kingston.mmorpg.framework.orm;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateQuerier extends HibernateDaoSupport implements Querier {

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryAll(Class<T> clazz, String namedQuery, Object... params) {
		return (List<T>) getHibernateTemplate().findByNamedQuery(namedQuery, params);
	}

}
