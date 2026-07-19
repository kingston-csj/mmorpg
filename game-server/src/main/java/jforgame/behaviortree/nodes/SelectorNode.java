package jforgame.behaviortree.nodes;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTNode;
import jforgame.behaviortree.BTStatus;

/**
 * 选择节点
 * 按顺序执行子节点，任何一个子节点成功就返回成功
 * 所有子节点都失败才返回失败
 *
 */
public class SelectorNode extends CompositeNode {
    
    public SelectorNode(String name) {
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
                    return BTStatus.SUCCESS;
                case FAILURE:
                    moveToNextChild();
                    break;
                case RUNNING:
                    return BTStatus.RUNNING;
                default:
                    moveToNextChild();
                    break;
            }
        }
        
        // 所有子节点都执行失败
        return BTStatus.FAILURE;
    }
}
