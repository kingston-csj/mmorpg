package org.forfun.mmorpg.game.buff.model;

import org.forfun.mmorpg.game.base.GameContext;

import java.util.concurrent.Future;

/**
 * 周期性buff
 * 
 *
 */
public abstract class PeriodicBuff extends Buff {

	/** 每一个tick执行的间隔 */
	private int periodicInterval;

	private Future<?> periodicTask;

	@Override
	public void init() {
		try {
			super.init();
		} finally {
			registerFrameTask();
		}
	}

	private void registerFrameTask() {
		cancleFrameTask();
		periodicTask = GameContext.getSchedulerManager().schedule(new Runnable() {
			@Override
			public void run() {
				enterFrame();
			}

		}, periodicInterval);
	}

	private void cancleFrameTask() {
		if (periodicTask != null) {
			periodicTask.cancel(false);
			periodicTask = null;
		}
	}

	@Override
	public void onDestroy() {
		try {
			super.onDestroy();
		} finally {
			cancleFrameTask();
		}
	}

	public abstract void enterFrame();

}
