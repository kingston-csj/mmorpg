package jforgame.behaviortree.nodes.conditions;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.nodes.ConditionNode;

/**
 * 黑板条件节点
 * 检查黑板中的值是否满足条件
 * 
 * 
 */
public class BlackboardCondition extends ConditionNode {
    
    /** 黑板键名 */
    private final String key;
    
    /** 期望值 */
    private final Object expectedValue;
    
    /** 比较操作类型 */
    public enum CompareType {
        EQUALS,         // 等于
        NOT_EQUALS,     // 不等于
        GREATER_THAN,   // 大于
        LESS_THAN,      // 小于
        GREATER_EQUAL,  // 大于等于
        LESS_EQUAL,     // 小于等于
        EXISTS,         // 存在
        NOT_EXISTS      // 不存在
    }
    
    private final CompareType compareType;
    
    public BlackboardCondition(String name, String key, Object expectedValue) {
        this(name, key, expectedValue, CompareType.EQUALS);
    }
    
    public BlackboardCondition(String name, String key, Object expectedValue, CompareType compareType) {
        super(name);
        this.key = key;
        this.expectedValue = expectedValue;
        this.compareType = compareType;
    }
    
    @Override
    protected boolean checkCondition(AIContext context) {
        Object actualValue = context.getBlackboard().get(key);
        
        switch (compareType) {
            case EXISTS:
                return actualValue != null;
            case NOT_EXISTS:
                return actualValue == null;
            case EQUALS:
                return expectedValue.equals(actualValue);
            case NOT_EQUALS:
                return !expectedValue.equals(actualValue);
            case GREATER_THAN:
                return compareNumbers(actualValue, expectedValue) > 0;
            case LESS_THAN:
                return compareNumbers(actualValue, expectedValue) < 0;
            case GREATER_EQUAL:
                return compareNumbers(actualValue, expectedValue) >= 0;
            case LESS_EQUAL:
                return compareNumbers(actualValue, expectedValue) <= 0;
            default:
                return false;
        }
    }
    
    /**
     * 比较两个数值
     */
    private int compareNumbers(Object actual, Object expected) {
        if (actual == null || expected == null) {
            return 0;
        }
        
        if (actual instanceof Number && expected instanceof Number) {
            double actualNum = ((Number) actual).doubleValue();
            double expectedNum = ((Number) expected).doubleValue();
            return Double.compare(actualNum, expectedNum);
        }
        
        return 0;
    }
}
