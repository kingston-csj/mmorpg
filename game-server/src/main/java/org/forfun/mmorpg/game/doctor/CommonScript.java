package org.forfun.mmorpg.game.doctor;

/**
 * 业务逻辑代码热更，代码写在构造方法，每次调用都是不同的类加载器
 * 
 */
public class CommonScript implements IScript {

	public CommonScript() {
		System.err.println("---CommonScript---");
	}

}
