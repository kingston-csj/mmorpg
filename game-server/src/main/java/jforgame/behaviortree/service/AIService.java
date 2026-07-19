package jforgame.behaviortree.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jforgame.behaviortree.AIConfig;
import jforgame.behaviortree.BehaviorTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jforgame.behaviortree.AIEntity;
import jforgame.behaviortree.AIManager;
import jforgame.behaviortree.BehaviorTreeBuilder;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

/**
 * AI服务
 * 提供AI相关的业务逻辑
 */
@Service
@Slf4j
public class AIService {

    @Autowired
    private AIManager aiManager;

    /**
     * AI配置映射
     */
    private final Map<String, AIConfig> aiConfigs = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // 初始化AI管理器
        aiManager.init();

        // 注册默认的行为树模板
        registerDefaultBehaviorTrees();

        log.info("AIService initialized successfully");
    }

    @PreDestroy
    public void destroy() {
        aiManager.shutdown();
        log.info("AIService destroyed");
    }

    /**
     * 注册默认的行为树模板
     */
    private void registerDefaultBehaviorTrees() {
        // 注册简单AI模板
        BehaviorTree simpleAI = BehaviorTreeBuilder.createSimpleAI("simple_ai", "Simple AI");
        aiManager.registerBehaviorTree("simple_ai", simpleAI);

        // 注册复杂AI模板
        BehaviorTree complexAI = BehaviorTreeBuilder.createComplexAI("complex_ai", "Complex AI");
        aiManager.registerBehaviorTree("complex_ai", complexAI);

        // 注册巡逻AI模板
        BehaviorTree patrolAI = createPatrolAI();
        aiManager.registerBehaviorTree("patrol_ai", patrolAI);

        // 注册战斗AI模板
        BehaviorTree combatAI = createCombatAI();
        aiManager.registerBehaviorTree("combat_ai", combatAI);
    }

    /**
     * 创建巡逻AI
     */
    private BehaviorTree createPatrolAI() {
        return new BehaviorTreeBuilder()
            .sequence("PatrolMain")
                .logAction("StartPatrol", "开始巡逻")
                .repeatForever("PatrolLoop")
                    .sequence("PatrolStep")
                        .logAction("MoveToPoint", "移动到下一个巡逻点")
                        .wait("MoveTime", 2000)
                        .logAction("ReachPoint", "到达巡逻点")
                        .wait("LookAround", 1000)
                    .end()
                .end()
            .end()
            .build("simple_patrol", "简单巡逻AI");
    }

    /**
     * 创建战斗AI
     */
    public static BehaviorTree createCombatAI() {
        return new BehaviorTreeBuilder()
            .selector("CombatMain")
                .sequence("AttackSequence")
                    .blackboardCondition("HasTarget", "hasTarget", true)
                    .blackboardCondition("InRange", "inRange", true)
                    .blackboardCondition("CanAttack", "canAttack", true)
                    .logAction("AttackTarget", "攻击目标")
                    .wait("AttackTime", 1000)
                    .setBlackboard("AttackCooldown", "canAttack", false)
                .end()
                .sequence("ChaseSequence")
                    .blackboardCondition("HasTarget", "hasTarget", true)
                    .blackboardCondition("NotInRange", "inRange", false)
                    .logAction("ChaseTarget", "追击目标")
                    .wait("ChaseTime", 2000)
                .end()
                .sequence("SearchSequence")
                    .blackboardCondition("NoTarget", "hasTarget", false)
                    .logAction("SearchEnemy", "搜索敌人")
                    .wait("SearchTime", 3000)
                .end()
            .end()
            .build("combat_ai", "战斗AI");
    }

    /**
     * 为实体创建AI
     */
    public AIEntity createAIForEntity(long entityId, String entityType, String aiType) {
        AIEntity aiEntity = aiManager.createAIEntity(entityId, entityType, aiType);
        if (aiEntity != null) {
            // 设置初始黑板数据
            initializeBlackboard(aiEntity, aiType);
            log.info("Created AI for entity {} with type {}", entityId, aiType);
        }
        return aiEntity;
    }

    /**
     * 初始化黑板数据
     */
    private void initializeBlackboard(AIEntity aiEntity, String aiType) {
        switch (aiType) {
            case "simple_ai":
                aiEntity.setBlackboardData("hasTarget", false);
                break;
            case "complex_ai":
                aiEntity.setBlackboardData("inCombat", false);
                aiEntity.setBlackboardData("canAttack", true);
                aiEntity.setBlackboardData("shouldDefend", false);
                aiEntity.setBlackboardData("health", 1.0);
                break;
            case "patrol_ai":
                aiEntity.setBlackboardData("patrolIndex", 0);
                aiEntity.setBlackboardData("patrolPoints", new int[]{1, 2, 3, 4});
                break;
            case "combat_ai":
                aiEntity.setBlackboardData("hasTarget", false);
                aiEntity.setBlackboardData("inRange", false);
                aiEntity.setBlackboardData("canAttack", true);
                break;
        }
    }

    /**
     * 移除实体的AI
     */
    public void removeAIFromEntity(long entityId) {
        aiManager.removeAIEntity(entityId);
        log.info("Removed AI from entity {}", entityId);
    }

    /**
     * 设置实体AI状态
     */
    public void setEntityAIEnabled(long entityId, boolean enabled) {
        aiManager.setAIEntityEnabled(entityId, enabled);
    }

    /**
     * 获取实体AI
     */
    public AIEntity getEntityAI(long entityId) {
        return aiManager.getAIEntity(entityId);
    }

    /**
     * 手动触发实体AI执行
     */
    public void triggerEntityAI(long entityId) {
        aiManager.tickAIEntity(entityId);
    }

    /**
     * 设置实体黑板数据
     */
    public void setEntityBlackboardData(long entityId, String key, Object value) {
        AIEntity aiEntity = aiManager.getAIEntity(entityId);
        if (aiEntity != null) {
            aiEntity.setBlackboardData(key, value);
        }
    }

    /**
     * 获取实体黑板数据
     */
    public Object getEntityBlackboardData(long entityId, String key) {
        AIEntity aiEntity = aiManager.getAIEntity(entityId);
        return aiEntity != null ? aiEntity.getBlackboardData(key) : null;
    }

    /**
     * 获取AI统计信息
     */
    public AIStatistics getAIStatistics() {
        return new AIStatistics(
                aiManager.getAIEntityCount(),
                aiManager.getEnabledAIEntityCount(),
                aiManager.isInitialized()
        );
    }

    /**
     * AI统计信息
     */
    public static class AIStatistics {
        private final int totalEntities;
        private final int enabledEntities;
        private final boolean managerInitialized;

        public AIStatistics(int totalEntities, int enabledEntities, boolean managerInitialized) {
            this.totalEntities = totalEntities;
            this.enabledEntities = enabledEntities;
            this.managerInitialized = managerInitialized;
        }

        // Getters
        public int getTotalEntities() {
            return totalEntities;
        }

        public int getEnabledEntities() {
            return enabledEntities;
        }

        public boolean isManagerInitialized() {
            return managerInitialized;
        }
    }
}
