package jforgame.behaviortree.nodes.conditions;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.nodes.ConditionNode;

/**
 * 时间条件节点
 * 检查时间相关的条件
 * 
 * 
 */
public class TimeCondition extends ConditionNode {
    
    /** 条件类型 */
    public enum TimeType {
        /** 距离上次执行超过指定时间 */
        TIME_SINCE_LAST_TICK,
        /** 距离开始时间超过指定时间 */
        TIME_SINCE_START,
        /** 当前时间在指定范围内 */
        TIME_IN_RANGE,
        /** 当前时间在指定范围外 */
        TIME_OUT_OF_RANGE
    }
    
    private final TimeType timeType;
    private final long timeValue; // 毫秒
    private final long startTime;
    
    public TimeCondition(String name, TimeType timeType, long timeValue) {
        super(name);
        this.timeType = timeType;
        this.timeValue = timeValue;
        this.startTime = System.currentTimeMillis();
    }
    
    @Override
    protected boolean checkCondition(AIContext context) {
        long currentTime = System.currentTimeMillis();
        
        switch (timeType) {
            case TIME_SINCE_LAST_TICK:
                return (currentTime - context.getLastTickTime()) >= timeValue;
            case TIME_SINCE_START:
                return (currentTime - startTime) >= timeValue;
            case TIME_IN_RANGE:
                // 这里需要根据具体需求实现时间范围检查
                return true; // 简化实现
            case TIME_OUT_OF_RANGE:
                // 这里需要根据具体需求实现时间范围检查
                return false; // 简化实现
            default:
                return false;
        }
    }
}
