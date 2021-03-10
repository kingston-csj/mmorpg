package org.forfun.mmorpg.game.scene.actor;

public abstract class GameObject {

	protected Long id;

	public Long getId() {
		return id;
	}

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
		GameObject other = (GameObject) obj;
		if (getId() != other.getId()) {
			return false;
		}
		return true;
	}

}
