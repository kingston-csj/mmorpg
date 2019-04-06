package com.kingston.mmorpg.game.buff.model;

import java.util.concurrent.Future;

import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.scene.actor.Creature;

public abstract class Buff {

	protected int modelId;

	/** 激活时间 */
	private long activeTime;

	/** 持续时长 */
	private long duration;

	private int overlapLayer;

	private Creature owner;

	private Creature origin;

	private Future<?> timeOutTask;

	public void init() {
		activeTime = System.currentTimeMillis();
		if (!isPermanent()) {
			registerTimeOutTask();
		}
		onStart();
	}

	public void onStart() {

	}

	public void registerTimeOutTask() {
		cancleTimeOutTask();
		timeOutTask = SpringContext.getSchedulerManager().schedule(new Runnable() {
			@Override
			public void run() {
				destroy();
			}
		}, getDuration());
	}

	public void cancleTimeOutTask() {
		if (timeOutTask != null) {
			timeOutTask.cancel(false);
			timeOutTask = null;
		}
	}

	/**
	 * 是否永久性buff
	 * 
	 * @return
	 */
	public boolean isPermanent() {
		return this.duration < 0;
	}

	public void destroy() {
		onDestroy();
	}

	public void onDestroy() {
		SpringContext.getBuffService().removeBuff(owner, this);
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public int getOverlapLayer() {
		return overlapLayer;
	}

	public void setOverlapLayer(int overlapLayer) {
		this.overlapLayer = overlapLayer;
	}

	public Creature getOwner() {
		return owner;
	}

	public void setOwner(Creature owner) {
		this.owner = owner;
	}

	public Creature getOrigin() {
		return origin;
	}

	public void setOrigin(Creature origin) {
		this.origin = origin;
	}

}
