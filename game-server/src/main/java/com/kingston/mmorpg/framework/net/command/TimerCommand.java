package com.kingston.mmorpg.framework.net.command;

public final class TimerCommand extends BaseCommand {

	private int currLoop;
	/** indicate loop task when it is smaller than 0 */
	private int maxLoop;

	public TimerCommand(int dispatchMap, int dispatchLine) {
		this(dispatchMap, dispatchLine, 1);
	}

	public TimerCommand(int dispatchMap, int dispatchLine, int maxLoop) {
		this.dispatchMap = dispatchMap;
		this.dispatchLine = dispatchLine;
		this.maxLoop = maxLoop;
	}

	public void updateLoopTimes() {
		this.currLoop += 1;
	}

	public boolean canRunAgain() {
		if (this.maxLoop <= 0) {
			return true;
		}
		return this.currLoop < this.maxLoop;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}
}
