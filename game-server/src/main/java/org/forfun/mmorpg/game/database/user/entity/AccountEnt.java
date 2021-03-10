package org.forfun.mmorpg.game.database.user.entity;

import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.forfun.mmorpg.game.database.user.dao.AccountDao;
import org.hibernate.annotations.Proxy;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accountent")
@Proxy(lazy = false)
public class AccountEnt implements BaseEntity<Long> {

	@Id
	@Column
	private long id;

	@Column
	private String name;

	@Override
	public Long getId() {
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
		return GameContext.getBean(AccountDao.class);
	}

}
