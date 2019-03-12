package com.kingston.mmorpg.game.buff.model;

import java.util.concurrent.Future;

import com.kingston.mmorpg.game.base.SpringContext;

/**
 * 周期性buff
 * @author kingston
 */
public abstract class PeriodicBuff extends Buff {
	
	/** 每一个tick执行的间隔 */
	private int periodicInterval;
	
	private Future<?> periodicTask;
	
	@Override
	public void init() {
		try{
			super.init();
		}finally{
			registerFrameTask();
		}
	}
	
	
	private void registerFrameTask() {
		cancleFrameTask();
		periodicTask = SpringContext.getSchedulerManager().schedule(
				new Runnable() {
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
