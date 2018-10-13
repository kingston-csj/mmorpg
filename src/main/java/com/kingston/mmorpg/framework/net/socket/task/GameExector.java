package com.kingston.mmorpg.framework.net.socket.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.util.thread.NamedThreadFactory;

@Component
public class GameExector {

	private static Logger logger = LoggerFactory.getLogger(GameExector.class);
	
	private static volatile GameExector instance;
	
	private final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
	/** task worker pool */
	private final ExecutorService[] workerPool = new ExecutorService[CORE_SIZE];

	private final AtomicBoolean run = new AtomicBoolean(true);
	
	@PostConstruct
	private void init() {
		for (int i=0; i<CORE_SIZE; i++) {
			ThreadFactory threadFactory = new NamedThreadFactory("message-task-handler");
			workerPool[i] = Executors.newSingleThreadExecutor(threadFactory);
		}
		instance = this;
	}
	
	public static GameExector getInstance() {
		return instance;
	}

	/**
	 * @param task
	 */
	public void acceptTask(BaseTask task) {
		if (task == null) {
			throw new NullPointerException("task is null");
		}
		int distributeKey = task.dispatchMap % CORE_SIZE;
		workerPool[distributeKey].submit(new Runnable() {
			@Override
			public void run() {
				task.run();
			}
		});
	}

	/**
	 * shut context
	 */
	public void shutDown() {
		run.set(false);
	}

}
