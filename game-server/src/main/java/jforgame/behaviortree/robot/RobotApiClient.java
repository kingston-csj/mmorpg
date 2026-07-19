package jforgame.behaviortree.robot;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 占位的机器人API客户端
 * 仅模拟请求调用与耗时，不依赖真实业务接口
 */
public class RobotApiClient {
	public boolean login(long playerId) {
		sleepRandom(20, 60);
		return true;
	}

	public boolean fetchQuests(long playerId) {
		sleepRandom(20, 80);
		return true;
	}

	public boolean submitQuest(long playerId, long questId) {
		sleepRandom(30, 120);
		return true;
	}

	public boolean fetchBag(long playerId) {
		sleepRandom(20, 60);
		return true;
	}

	public boolean useItem(long playerId, int itemId, int count) {
		sleepRandom(20, 80);
		return true;
	}

	public boolean lotteryDrawOnce(long playerId) {
		sleepRandom(50, 150);
		return true;
	}

	public boolean lotteryDrawTen(long playerId) {
		sleepRandom(120, 260);
		return true;
	}

	private void sleepRandom(int minMs, int maxMs) {
		int ms = ThreadLocalRandom.current().nextInt(minMs, maxMs + 1);
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}


