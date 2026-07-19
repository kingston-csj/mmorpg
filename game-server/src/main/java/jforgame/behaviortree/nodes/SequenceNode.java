package jforgame.behaviortree.nodes;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTNode;
import jforgame.behaviortree.BTStatus;

/**
 * 带记忆的序列节点
 * 按顺序执行子节点，所有子节点都成功才返回成功
 * 任何一个子节点失败则立即返回失败
 * 如果子节点返回RUNNING，下一次 tick 会再次触发该子节点，直到子节点返回SUCCESS或FAILURE
 * 
 * 
 */
public class SequenceNode extends CompositeNode {
    
    public SequenceNode(String name) {
        super(name);
    }
    
    @Override
    protected BTStatus execute(AIContext context) {
        while (hasMoreChildren()) {
            BTNode currentChild = getCurrentChild();
            if (currentChild == null) {
                return BTStatus.FAILURE;
            }
            
            BTStatus childStatus = currentChild.tick(context);
            
            switch (childStatus) {
                case SUCCESS:
                    moveToNextChild();
                    break;
                case FAILURE:
                    return BTStatus.FAILURE;
                case RUNNING:
                    return BTStatus.RUNNING;
                default:
                    return BTStatus.FAILURE;
            }
        }
        
        // 所有子节点都执行成功
        return BTStatus.SUCCESS;
    }
}
