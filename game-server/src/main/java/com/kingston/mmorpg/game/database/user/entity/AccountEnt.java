package com.kingston.mmorpg.game.database.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
import org.springframework.data.repository.CrudRepository;

import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.database.user.BaseEntity;
import com.kingston.mmorpg.game.database.user.dao.AccountDao;

@Entity
@Table(name = "AccountEnt")
@Proxy(lazy = false)
public class AccountEnt implements BaseEntity {

	@Id
	@Column
	private long id;

	@Column
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public CrudRepository<AccountEnt, Long> getCrudRepository() {
		return SpringContext.getBean(AccountDao.class);
	}

}
