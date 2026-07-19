package jforgame.behaviortree.nodes;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTNode;
import jforgame.behaviortree.BTStatus;

/**
 * 反转节点
 * 反转子节点的执行结果
 * SUCCESS -> FAILURE, FAILURE -> SUCCESS, RUNNING -> RUNNING
 */
public class InverterNode extends DecoratorNode {

    public InverterNode(String name) {
        super(name);
    }

    public InverterNode(String name, BTNode child) {
        super(name, child);
    }

    @Override
    protected BTStatus execute(AIContext context) {
        if (child == null) {
            return BTStatus.FAILURE;
        }

        BTStatus childStatus = child.tick(context);

        switch (childStatus) {
            case SUCCESS:
                return BTStatus.FAILURE;
            case FAILURE:
                return BTStatus.SUCCESS;
            case RUNNING:
                return BTStatus.RUNNING;
            default:
                return BTStatus.FAILURE;
        }
    }
}
