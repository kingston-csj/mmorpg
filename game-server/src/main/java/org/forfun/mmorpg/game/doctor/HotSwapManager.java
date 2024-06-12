package org.forfun.mmorpg.game.doctor;

import java.lang.management.ManagementFactory;


import jakarta.annotation.PostConstruct;
import jforgame.commons.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.tools.attach.VirtualMachine;

import groovy.lang.GroovyClassLoader;

@Component
public class HotSwapManager {

	private Logger logger = LoggerFactory.getLogger(HotSwapManager.class);

	private static HotSwapManager self;

	private final String EXEC_SUCC = "热更成功";
	private final String EXEC_FAILED = "热更失败";

	@PostConstruct
	private void init() {
		self = new HotSwapManager();
	}

	public static HotSwapManager getInstance() {
		return self;
	}

	public String hotSwap(String fileName) {
		if ("Common".equalsIgnoreCase(fileName)) {
			return hotSwapScript();
		}

		if (reloadClass(fileName)) {
			return EXEC_SUCC;
		}

		return EXEC_FAILED;
	}

	/**
	 * 使用groovy类加载器重载java代码 重载的java文件可以直接使用源文件，无需编译为class
	 * 
	 * @return
	 */
	public String hotSwapScript() {
		try {
			String filePath = "hotswap/CommonScript.java";
			String clazzFile = FileUtils.readFullText(filePath);
			@SuppressWarnings({ "resource", "unchecked" })
			Class<IScript> clazz = new GroovyClassLoader().parseClass(clazzFile);
			clazz.newInstance();
		} catch (Exception e) {
			logger.error("", e);
			return EXEC_FAILED;
		}

		return EXEC_SUCC;
	}

	/**
	 * 使用jdk instrument 来重新加载内存中的class 你只能修改方法体的代码，而不能动态增删方法
	 * 
	 * @param className
	 * @return
	 */
	private boolean reloadClass(String className) {
		try {
			// 拿到当前jvm的进程id
			String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
			VirtualMachine vm = VirtualMachine.attach(pid);
			String[] classStr = className.split("\\.");
			String path = "./hotswap/" + classStr[classStr.length - 1] + ".class";
			System.err.println("path==" + path);
			// path参数即agentmain()方法的第一个参数
			vm.loadAgent("libs/hotswap-agent.jar", path);
			return true;
		} catch (Exception e) {
			logger.error("", e);
		}

		return true;
	}

}
