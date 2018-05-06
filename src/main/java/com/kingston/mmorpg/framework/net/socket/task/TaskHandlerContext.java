package com.kingston.mmorpg.framework.net.socket.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kingston.mmorpg.game.util.NamedThreadFactory;

public class TaskHandlerContext {


	private static Logger logger = LoggerFactory.getLogger(TaskHandlerContext.class);
	
	private static volatile TaskHandlerContext instance;
	
	private final int CORE_SIZE = Runtime.getRuntime().availableProcessors();
	/** task worker pool */
	private final ExecutorService[] workerPool = new ExecutorService[CORE_SIZE];

	private final AtomicBoolean run = new AtomicBoolean(true);
	
	public static TaskHandlerContext getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized (TaskHandlerContext.class) {
			if (instance == null) {
				instance = new TaskHandlerContext();
				instance.initialize();
			}
		}
		return instance;
	}

	public void initialize() {
		for (int i=0; i<CORE_SIZE; i++) {
			ThreadFactory threadFactory = new NamedThreadFactory("message-task-handler");
			workerPool[i] = Executors.newSingleThreadExecutor(threadFactory);
		}
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
