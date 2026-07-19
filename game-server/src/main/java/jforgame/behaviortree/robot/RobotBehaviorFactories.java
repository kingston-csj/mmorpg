package jforgame.behaviortree.robot;

import jforgame.behaviortree.BehaviorTree;
import jforgame.behaviortree.AIManager;
import jforgame.behaviortree.BehaviorTreeBuilder;

/**
 * 机器人行为树工厂（使用标准 BehaviorTreeBuilder）
 */
public class RobotBehaviorFactories {
	public static final String QUEST_BOT = "robot_quest";
	public static final String BAG_BOT = "robot_bag";
	public static final String LOTTERY_BOT = "robot_lottery";
	public static final String MIXED_BOT = "robot_mixed";

	/** 注册所有机器人行为树模板到 AIManager */
	public static void registerAll(AIManager manager) {
		manager.registerBehaviorTree(QUEST_BOT, questBot());
		manager.registerBehaviorTree(BAG_BOT, bagBot());
		manager.registerBehaviorTree(LOTTERY_BOT, lotteryBot());
		manager.registerBehaviorTree(MIXED_BOT, mixedBot());
	}

	public static BehaviorTree questBot() {
		return new BehaviorTreeBuilder()
			.sequence("QuestBot")
				.logAction("Login", "Robot login")
				.logAction("FetchQuests", "Fetch quests")
				.logAction("SubmitRandomQuest", "Submit a random quest")
				.wait("IdleWait", 300)
			.end()
			.build(QUEST_BOT, "QuestBot");
	}

	public static BehaviorTree bagBot() {
		return new BehaviorTreeBuilder()
			.sequence("BagBot")
				.logAction("Login", "Robot login")
				.logAction("FetchBag", "Fetch bag")
				.logAction("UseRandomItem", "Use a random item")
				.wait("IdleWait", 300)
			.end()
			.build(BAG_BOT, "BagBot");
	}

	public static BehaviorTree lotteryBot() {
		return new BehaviorTreeBuilder()
			.selector("LotteryBot")
				.sequence("TenDraw")
					.logAction("Login", "Robot login")
					.logAction("DrawTen", "Lottery draw 10")
					.wait("WaitAfterTen", 500)
				.end()
				.sequence("SingleDrawFallback")
					.logAction("Login2", "Robot login (fallback)")
					.logAction("DrawOnce", "Lottery draw once")
					.wait("WaitAfterOne", 300)
				.end()
			.end()
			.build(LOTTERY_BOT, "LotteryBot");
	}

	public static BehaviorTree mixedBot() {
		return new BehaviorTreeBuilder()
			.selector("MixedBot")
				.sequence("QuestFlow")
					.logAction("LoginQ", "Robot login")
					.logAction("FetchQuests", "Fetch quests")
					.logAction("SubmitRandomQuest", "Submit a random quest")
				.end()
				.sequence("BagFlow")
					.logAction("FetchBag", "Fetch bag")
					.logAction("UseRandomItem", "Use a random item")
				.end()
				.sequence("LotteryFlow")
					.logAction("DrawTen", "Lottery draw 10")
					.wait("FlowIdle", 400)
				.end()
			.end()
			.build(MIXED_BOT, "MixedBot");
	}
}


