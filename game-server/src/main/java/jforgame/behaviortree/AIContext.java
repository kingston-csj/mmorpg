package jforgame.behaviortree;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;

/**
 * AI上下文，包含AI执行所需的所有信息
 * 每个具有AI属性的实体都有一个AIContext。多个{@link BehaviorTree}，例如一群怪物，可共享同一个AIContext
 */
@Data
public class AIContext {

    /**
     * AI实体ID
     */
    private long entityId;

    /**
     * AI实体类型
     */
    private String entityType;

    /**
     * 当前时间戳
     */
    private long currentTime;

    /**
     * 上次执行时间
     */
    private long lastTickTime;

    /**
     * 执行间隔（毫秒）
     */
    private long tickInterval = 100;

    /**
     * 黑板数据
     */
    private Map<String, Object> blackboard = new ConcurrentHashMap<>();

    /**
     * 行为树引用
     */
    private Object behaviorTree;

    /**
     * 自定义数据存储
     */
    private Map<String, Object> customData = new ConcurrentHashMap<>();

    public AIContext(long entityId, String entityType) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.currentTime = System.currentTimeMillis();
        this.lastTickTime = 0;
    }

    /**
     * 更新当前时间
     */
    public void updateTime() {
        this.currentTime = System.currentTimeMillis();
    }

    /**
     * 检查是否可以执行tick
     */
    public boolean canTick() {
        return currentTime - lastTickTime >= tickInterval;
    }

    /**
     * 更新上次执行时间
     */
    public void updateLastTickTime() {
        this.lastTickTime = currentTime;
    }

    /**
     * 设置上次执行时间
     */
    public void setLastTickTime(long lastTickTime) {
        this.lastTickTime = lastTickTime;
    }

    /**
     * 设置自定义数据
     */
    public void setCustomData(String key, Object value) {
        if (customData == null) {
            customData = new java.util.concurrent.ConcurrentHashMap<>();
        }
        customData.put(key, value);
    }

    /**
     * 获取自定义数据
     */
    @SuppressWarnings("unchecked")
    public <T> T getCustomData(String key) {
        return customData == null ? null : (T) customData.get(key);
    }

    /**
     * 获取自定义数据，带默认值
     */
    @SuppressWarnings("unchecked")
    public <T> T getCustomData(String key, T defaultValue) {
        return customData == null ? defaultValue : (T) customData.getOrDefault(key, defaultValue);
    }

    /**
     * 设置黑板数据
     */
    public void setBlackboard(Map<String, Object> blackboard) {
        this.blackboard = blackboard;
    }

    /**
     * 设置行为树
     */
    public void setBehaviorTree(Object behaviorTree) {
        this.behaviorTree = behaviorTree;
    }

    /**
     * 获取行为树
     */
    public Object getBehaviorTree() {
        return this.behaviorTree;
    }

    /**
     * 获取黑板数据
     */
    public Map<String, Object> getBlackboard() {
        return blackboard;
    }
}
