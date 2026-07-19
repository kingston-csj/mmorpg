package jforgame.behaviortree.nodes;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTNode;

/**
 * 复合节点基类
 * 用于组合多个子节点的行为
 * 
 * 
 */
public abstract class CompositeNode extends BTNode {
    
    /** 当前执行的子节点索引 */
    protected int currentChildIndex = 0;
    
    public CompositeNode(String name) {
        super(name);
    }
    
    @Override
    protected void onStart(AIContext context) {
        super.onStart(context);
        currentChildIndex = 0;
    }
    
    @Override
    public void reset() {
        super.reset();
        currentChildIndex = 0;
    }
    
    /**
     * 获取当前子节点
     */
    protected BTNode getCurrentChild() {
        if (currentChildIndex < children.size()) {
            return children.get(currentChildIndex);
        }
        return null;
    }
    
    /**
     * 移动到下一个子节点
     */
    protected void moveToNextChild() {
        currentChildIndex++;
    }
    
    /**
     * 重置到第一个子节点
     */
    protected void resetToFirstChild() {
        currentChildIndex = 0;
    }
    
    /**
     * 检查是否还有子节点需要执行
     */
    protected boolean hasMoreChildren() {
        return currentChildIndex < children.size();
    }
}
