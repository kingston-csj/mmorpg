package org.forfun.mmorpg.game.doctor;

import org.springframework.cache.annotation.SpringCacheAnnotationParser;

import org.forfun.mmorpg.game.base.SpringContext;

/**
 * 业务逻辑代码热更，代码写在构造方法，每次调用都是不同的类加载器
 * 
 *
 *
 */
public class CommonScript implements IScript {
	
	public CommonScript() {
		System.err.println("---CommonScript--");
	}

}
