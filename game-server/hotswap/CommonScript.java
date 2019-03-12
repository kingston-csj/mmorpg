package com.kingston.mmorpg.game.doctor;

import org.springframework.cache.annotation.SpringCacheAnnotationParser;

import com.kingston.mmorpg.game.base.SpringContext;

/**
 * 业务逻辑代码热更，代码写在构造方法，每次调用都是不同的类加载器
 * 
 * @author kingston
 *
 */
public class CommonScript implements IScript {
	
	public CommonScript() {
		System.err.println("---CommonScript--");
	}

}
