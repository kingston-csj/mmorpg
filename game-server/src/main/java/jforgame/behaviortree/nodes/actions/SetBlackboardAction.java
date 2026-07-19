package jforgame.behaviortree.nodes.actions;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTStatus;
import jforgame.behaviortree.nodes.ActionNode;

/**
 * 设置黑板数据动作节点
 * 用于设置黑板中的值
 * 
 * 
 */
public class SetBlackboardAction extends ActionNode {
    
    /** 黑板键名 */
    private final String key;
    
    /** 要设置的值 */
    private final Object value;
    
    public SetBlackboardAction(String name, String key, Object value) {
        super(name);
        this.key = key;
        this.value = value;
    }
    
    @Override
    protected BTStatus performAction(AIContext context) {
        context.getBlackboard().put(key, value);
        return BTStatus.SUCCESS;
    }
}
