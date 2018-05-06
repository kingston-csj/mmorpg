package com.kingston.mmorpg.framework.orm;

import java.io.Serializable;

public interface Accessor {
	
	<PK extends Serializable, T extends Entity> T load (Class<T> clazz, PK id);
	
	<PK extends Serializable, T extends Entity> PK save (Class<T> clazz, T entity);

}
