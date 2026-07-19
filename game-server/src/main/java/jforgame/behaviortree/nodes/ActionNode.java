package jforgame.behaviortree.nodes;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTNode;
import jforgame.behaviortree.BTStatus;

/**
 * 动作节点基类
 * 用于执行具体的AI行为
 * 
 * 
 */
public abstract class ActionNode extends BTNode {
    
    public ActionNode(String name) {
        super(name);
    }
    
    @Override
    protected BTStatus execute(AIContext context) {
        return performAction(context);
    }
    
    /**
     * 子类需要实现的具体动作逻辑
     * @param context AI上下文
     * @return 动作执行状态
     */
    protected abstract BTStatus performAction(AIContext context);
}
