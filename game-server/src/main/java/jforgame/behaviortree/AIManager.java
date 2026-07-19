package jforgame.behaviortree;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * AI管理器
 * 管理所有AI实体的行为树执行
 */
@Component
@Slf4j
public class AIManager {

    /**
     * AI实体映射
     */
    private final Map<Long, jforgame.behaviortree.AIEntity> aiEntities = new ConcurrentHashMap<>();

    /**
     * 行为树模板映射
     */
    private final Map<String, jforgame.behaviortree.BehaviorTree> behaviorTreeTemplates = new ConcurrentHashMap<>();

    /**
     * AI执行调度器
     */
    private ScheduledExecutorService aiScheduler;

    /**
     * 是否已初始化
     */
    private boolean initialized = false;

    /**
     * AI执行间隔（毫秒）
     */
    private long aiTickInterval = 100;

    /**
     * 初始化AI管理器
     */
    public void init() {
        if (initialized) {
            return;
        }

        // 创建AI调度器
        aiScheduler = Executors.newScheduledThreadPool(4, r -> {
            Thread thread = new Thread(r, "AI-Thread");
            thread.setDaemon(true);
            return thread;
        });

        // 启动AI执行任务
        aiScheduler.scheduleAtFixedRate(this::tickAllAI, 0, aiTickInterval, TimeUnit.MILLISECONDS);

        initialized = true;
        log.info("AIManager initialized successfully");
    }

    /**
     * 注册行为树模板
     */
    public void registerBehaviorTree(String treeId, jforgame.behaviortree.BehaviorTree behaviorTree) {
        behaviorTreeTemplates.put(treeId, behaviorTree);
        log.info("Registered behavior tree template: {}", treeId);
    }

    /**
     * 创建AI实体
     */
    public jforgame.behaviortree.AIEntity createAIEntity(long entityId, String entityType, String behaviorTreeId) {
        jforgame.behaviortree.BehaviorTree template = behaviorTreeTemplates.get(behaviorTreeId);
        if (template == null) {
            log.error("Behavior tree template not found: {}", behaviorTreeId);
            return null;
        }

        // 创建行为树实例
        jforgame.behaviortree.BehaviorTree instance = new jforgame.behaviortree.BehaviorTree(
                template.getTreeId() + "_" + entityId,
                template.getTreeName() + "_" + entityId,
                template.getRootNode() // 这里需要深拷贝，简化处理
        );

        // 创建AI实体
        jforgame.behaviortree.AIEntity aiEntity = new jforgame.behaviortree.AIEntity(entityId, entityType, instance);
        aiEntities.put(entityId, aiEntity);

        log.info("Created AI entity: {} with behavior tree: {}", entityId, behaviorTreeId);
        return aiEntity;
    }

    /**
     * 移除AI实体
     */
    public void removeAIEntity(long entityId) {
        jforgame.behaviortree.AIEntity aiEntity = aiEntities.remove(entityId);
        if (aiEntity != null) {
            aiEntity.destroy();
            log.info("Removed AI entity: {}", entityId);
        }
    }

    /**
     * 获取AI实体
     */
    public jforgame.behaviortree.AIEntity getAIEntity(long entityId) {
        return aiEntities.get(entityId);
    }

    /**
     * 启用/禁用AI实体
     */
    public void setAIEntityEnabled(long entityId, boolean enabled) {
        jforgame.behaviortree.AIEntity aiEntity = aiEntities.get(entityId);
        if (aiEntity != null) {
            aiEntity.setEnabled(enabled);
        }
    }

    /**
     * 执行所有AI实体
     */
    private void tickAllAI() {
        try {
            for (jforgame.behaviortree.AIEntity aiEntity : aiEntities.values()) {
                if (aiEntity.isEnabled()) {
                    aiEntity.tick();
                }
            }
        } catch (Exception e) {
            log.error("Error during AI tick", e);
        }
    }

    /**
     * 手动执行指定AI实体
     */
    public void tickAIEntity(long entityId) {
        jforgame.behaviortree.AIEntity aiEntity = aiEntities.get(entityId);
        if (aiEntity != null && aiEntity.isEnabled()) {
            aiEntity.tick();
        }
    }

    /**
     * 获取AI实体数量
     */
    public int getAIEntityCount() {
        return aiEntities.size();
    }

    /**
     * 获取启用的AI实体数量
     */
    public int getEnabledAIEntityCount() {
        return (int) aiEntities.values().stream().filter(AIEntity::isEnabled).count();
    }

    /**
     * 关闭AI管理器
     */
    public void shutdown() {
        if (aiScheduler != null && !aiScheduler.isShutdown()) {
            aiScheduler.shutdown();
            try {
                if (!aiScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    aiScheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                aiScheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        // 清理所有AI实体
        aiEntities.clear();
        behaviorTreeTemplates.clear();

        initialized = false;
        log.info("AIManager shutdown completed");
    }

    // Getters
    public boolean isInitialized() {
        return initialized;
    }

    public long getAiTickInterval() {
        return aiTickInterval;
    }

    public void setAiTickInterval(long aiTickInterval) {
        this.aiTickInterval = aiTickInterval;
    }
}
