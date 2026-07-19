package jforgame.behaviortree.nodes;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTNode;
import jforgame.behaviortree.BTStatus;

/**
 * 重复节点
 * 重复执行子节点指定次数
 * 
 * 
 */
public class RepeatNode extends DecoratorNode {
    
    /** 重复次数，-1表示无限重复 */
    private final int repeatCount;
    
    /** 当前重复次数 */
    private int currentCount = 0;
    
    public RepeatNode(String name, int repeatCount) {
        super(name);
        this.repeatCount = repeatCount;
    }
    
    public RepeatNode(String name, BTNode child, int repeatCount) {
        super(name, child);
        this.repeatCount = repeatCount;
    }
    
    @Override
    protected void onStart(AIContext context) {
        super.onStart(context);
        currentCount = 0;
    }
    
    @Override
    protected BTStatus execute(AIContext context) {
        if (child == null) {
            return BTStatus.FAILURE;
        }
        
        // 无限重复
        if (repeatCount == -1) {
            BTStatus childStatus = child.tick(context);
            if (childStatus == BTStatus.SUCCESS) {
                child.reset(); // 重置子节点以重新执行
            }
            return childStatus;
        }
        
        // 有限重复
        while (currentCount < repeatCount) {
            BTStatus childStatus = child.tick(context);
            
            switch (childStatus) {
                case SUCCESS:
                    currentCount++;
                    child.reset(); // 重置子节点以重新执行
                    break;
                case FAILURE:
                    return BTStatus.FAILURE;
                case RUNNING:
                    return BTStatus.RUNNING;
            }
        }
        
        return BTStatus.SUCCESS;
    }
    
    @Override
    public void reset() {
        super.reset();
        currentCount = 0;
    }
}
