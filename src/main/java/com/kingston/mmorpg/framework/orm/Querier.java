package com.kingston.mmorpg.framework.orm;

import java.util.List;

public interface Querier {

	<T> List<T> queryAll(Class<T> clazz, String namedQuery, Object... params);
	
}
