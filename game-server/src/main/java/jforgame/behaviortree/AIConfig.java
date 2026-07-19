package jforgame.behaviortree;

import lombok.Data;

/**
 * AI配置类
 * 用于配置AI行为参数
 * 
 */
@Data
public class AIConfig {
    
    /** AI类型 */
    private String aiType;
    
    /** AI名称 */
    private String aiName;
    
    /** 执行间隔（毫秒） */
    private long tickInterval = 100;
    
    /** 是否启用 */
    private boolean enabled = true;
    
    /** 最大执行时间（毫秒） */
    private long maxExecutionTime = 1000;
    
    /** 是否记录详细日志 */
    private boolean verboseLogging = false;
    
    /** 自定义参数 */
    private java.util.Map<String, Object> parameters;
    
    public AIConfig() {
        this.parameters = new java.util.concurrent.ConcurrentHashMap<>();
    }
    
    public AIConfig(String aiType, String aiName) {
        this();
        this.aiType = aiType;
        this.aiName = aiName;
    }
    
    /**
     * 设置参数
     */
    public void setParameter(String key, Object value) {
        parameters.put(key, value);
    }
    
    /**
     * 获取参数
     */
    @SuppressWarnings("unchecked")
    public <T> T getParameter(String key) {
        return (T) parameters.get(key);
    }
    
    /**
     * 获取参数，带默认值
     */
    @SuppressWarnings("unchecked")
    public <T> T getParameter(String key, T defaultValue) {
        return (T) parameters.getOrDefault(key, defaultValue);
    }
    
    /**
     * 移除参数
     */
    public void removeParameter(String key) {
        parameters.remove(key);
    }
}
