package org.forfun.mmorpg.game.database.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.forfun.mmorpg.game.database.user.dao.AccountDao;
import org.hibernate.annotations.Proxy;
import org.springframework.data.repository.CrudRepository;


@Entity
@Table(name = "accountent")
@Proxy(lazy = false)
@Data
public class AccountEnt implements BaseEntity<Long> {

	@Id
	@Column
	private Long id;

	@Column
	private String name;

	@Override
	public CrudRepository<AccountEnt, Long> getCrudRepository() {
		return GameContext.getBean(AccountDao.class);
	}

}
