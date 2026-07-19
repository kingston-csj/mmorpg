package jforgame.behaviortree.nodes;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTNode;
import jforgame.behaviortree.BTStatus;

/**
 * 装饰器节点基类
 * 用于修改子节点的行为
 *
 * 
 */
public abstract class DecoratorNode extends BTNode {

    /**
     * 子节点
     */
    protected BTNode child;

    public DecoratorNode(String name) {
        super(name);
    }

    public DecoratorNode(String name, BTNode child) {
        super(name);
        setChild(child);
    }

    /**
     * 设置子节点
     */
    public void setChild(BTNode child) {
        if (this.child != null) {
            this.child.setParent(null);
        }
        this.child = child;
        if (child != null) {
            child.setParent(this);
        }
    }

    /**
     * 获取子节点
     */
    public BTNode getChild() {
        return child;
    }

    @Override
    public void reset() {
        super.reset();
        if (child != null) {
            child.reset();
        }
    }

    @Override
    protected BTStatus execute(AIContext context) {
        if (child == null) {
            return BTStatus.FAILURE;
        }

        return child.tick(context);
    }
}
