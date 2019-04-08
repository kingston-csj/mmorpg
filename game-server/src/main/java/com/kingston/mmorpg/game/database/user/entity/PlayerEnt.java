package com.kingston.mmorpg.game.database.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
import org.springframework.data.repository.CrudRepository;

import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.database.user.BaseEntity;
import com.kingston.mmorpg.game.database.user.dao.PlayerDao;

import lombok.Data;

@Entity
@Table(name = "PlayerEnt")
@Proxy(lazy = false)
@Data
public class PlayerEnt implements BaseEntity {

	@Id
	@Column
	private long playerId;

	/**
	 * 所属账号id
	 */
	@Column
	private long accountId;

	@Column
	private String name;

	@Column
	private int level;

	@Column
	private String data;

	@Override
	public CrudRepository<PlayerEnt, Long> getCrudRepository() {
		return SpringContext.getBean(PlayerDao.class);
	}

}
