package jforgame.behaviortree.nodes.actions;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTStatus;
import jforgame.behaviortree.nodes.ActionNode;

/**
 * 等待动作节点
 * 等待指定时间后返回成功
 * 
 * 
 */
public class WaitAction extends ActionNode {
    
    /** 等待时间（毫秒） */
    private final long waitTime;
    
    /** 开始等待的时间 */
    private long startTime;
    
    public WaitAction(String name, long waitTime) {
        super(name);
        this.waitTime = waitTime;
    }
    
    @Override
    protected void onStart(AIContext context) {
        super.onStart(context);
        startTime = System.currentTimeMillis();
    }
    
    @Override
    protected BTStatus performAction(AIContext context) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        
        if (elapsedTime >= waitTime) {
            return BTStatus.SUCCESS;
        } else {
            return BTStatus.RUNNING;
        }
    }
}
