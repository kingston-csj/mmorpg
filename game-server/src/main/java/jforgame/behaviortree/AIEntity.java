package jforgame.behaviortree;

import lombok.extern.slf4j.Slf4j;

/**
 * AI实体
 * 代表一个具有AI行为的游戏实体
 */
@Slf4j
public class AIEntity {

    /**
     * 实体ID
     */
    private final long entityId;

    /**
     * 实体类型
     */
    private final String entityType;

    /**
     * 行为树
     */
    private final BehaviorTree behaviorTree;

    /**
     * AI上下文
     */
    private final AIContext context;

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 创建时间
     */
    private final long createTime;

    /**
     * 最后执行时间
     */
    private long lastTickTime = 0;

    public AIEntity(long entityId, String entityType, BehaviorTree behaviorTree) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.behaviorTree = behaviorTree;
        this.context = new AIContext(entityId, entityType);
        this.createTime = System.currentTimeMillis();
    }

    /**
     * 执行AI逻辑
     */
    public void tick() {
        if (!enabled) {
            return;
        }

        try {
            // 更新上下文时间
            context.updateTime();

            // 检查是否可以执行
            if (!context.canTick()) {
                return;
            }

            // 执行行为树
            BTStatus status = behaviorTree.tick(context);

            // 更新最后执行时间
            context.updateLastTickTime();
            lastTickTime = System.currentTimeMillis();

            // 记录执行状态
            if (log.isDebugEnabled()) {
                log.debug("AI Entity[{}] tick result: {}", entityId, status);
            }

        } catch (Exception e) {
            log.error("AI Entity[{}] tick error", entityId, e);
        }
    }

    /**
     * 设置黑板数据
     */
    public void setBlackboardData(String key, Object value) {
        context.getBlackboard().put(key, value);
    }

    /**
     * 获取黑板数据
     */
    @SuppressWarnings("unchecked")
    public Object getBlackboardData(String key) {
        return context.getBlackboard().get(key);
    }

    /**
     * 移除黑板数据
     */
    public void removeBlackboardData(String key) {
        context.getBlackboard().remove(key);
    }

    /**
     * 重置AI实体
     */
    public void reset() {
        behaviorTree.reset();
        context.setLastTickTime(0);
        lastTickTime = 0;
    }

    /**
     * 销毁AI实体
     */
    public void destroy() {
        enabled = false;
        behaviorTree.reset();
        log.info("AI Entity[{}] destroyed", entityId);
    }

    // Getters
    public long getEntityId() {
        return entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public BehaviorTree getBehaviorTree() {
        return behaviorTree;
    }

    public AIContext getContext() {
        return context;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getLastTickTime() {
        return lastTickTime;
    }

    public BTStatus getCurrentStatus() {
        return behaviorTree.getCurrentStatus();
    }
}
