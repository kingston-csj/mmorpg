package com.kingston.mmorpg.game.database.user;

import java.io.Serializable;


@SuppressWarnings("serial")
public abstract class BaseEntity implements Serializable {

	/**
	 * entity id
	 * @return
	 */
	public abstract long getId() ;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Long.valueOf(getId()).hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		if (getId() != other.getId()) {
			return false;
		}
		return true;
	}

}