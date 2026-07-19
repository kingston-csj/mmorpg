package jforgame.behaviortree.nodes;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTNode;
import jforgame.behaviortree.BTStatus;

/**
 * 条件节点基类
 * 用于检查特定条件
 * 
 * 
 */
public abstract class ConditionNode extends BTNode {
    
    public ConditionNode(String name) {
        super(name);
    }
    
    @Override
    protected BTStatus execute(AIContext context) {
        boolean condition = checkCondition(context);
        return condition ? BTStatus.SUCCESS : BTStatus.FAILURE;
    }
    
    /**
     * 子类需要实现的条件检查逻辑
     * @param context AI上下文
     * @return 条件是否满足
     */
    protected abstract boolean checkCondition(AIContext context);
}
