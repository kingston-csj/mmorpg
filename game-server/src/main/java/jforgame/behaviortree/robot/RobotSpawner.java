package jforgame.behaviortree.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jforgame.behaviortree.AIEntity;
import jforgame.behaviortree.AIManager;
import jforgame.behaviortree.service.AIService;

import jakarta.annotation.PostConstruct;

/**
 * 简单的机器人生成器：启动时创建若干个机器人并运行（使用标准AI）
 */
@Component
public class RobotSpawner {
	@Autowired
	private AIService aiService;

	@Autowired
	private AIManager aiManager;

	private final List<Long> robotIds = new ArrayList<>();

	@PostConstruct
	public void init() {
		// 确保AI管理器已初始化
		aiManager.init();
		// 注册机器人行为树模板
		RobotBehaviorFactories.registerAll(aiManager);

		int robots = 10; // 可根据配置调整
		for (int i = 0; i < robots; i++) {
			long id = 100000 + i;
			String btId = pickRandomBehaviorId();
			AIEntity e = aiService.createAIForEntity(id, "Robot", btId);
			aiService.setEntityAIEnabled(id, true);
			robotIds.add(id);
		}
	}

	private String pickRandomBehaviorId() {
		int r = ThreadLocalRandom.current().nextInt(3);
		switch (r) {
			case 0: return RobotBehaviorFactories.QUEST_BOT;
			case 1: return RobotBehaviorFactories.BAG_BOT;
			default: return RobotBehaviorFactories.LOTTERY_BOT;
		}
	}
}


