package jforgame.behaviortree.nodes;

import jforgame.behaviortree.AIContext;
import jforgame.behaviortree.BTNode;
import jforgame.behaviortree.BTStatus;

/**
 * 并行节点
 * 同时执行所有子节点
 * 
 * 
 */
public class ParallelNode extends CompositeNode {
    
    /** 并行策略 */
    public enum Policy {
        /** 所有子节点成功才成功 */
        ALL_SUCCESS,
        /** 任何一个子节点成功就成功 */
        ANY_SUCCESS,
        /** 所有子节点失败才失败 */
        ALL_FAILURE,
        /** 任何一个子节点失败就失败 */
        ANY_FAILURE
    }
    
    private final Policy successPolicy;
    private final Policy failurePolicy;
    
    public ParallelNode(String name) {
        this(name, Policy.ALL_SUCCESS, Policy.ANY_FAILURE);
    }
    
    public ParallelNode(String name, Policy successPolicy, Policy failurePolicy) {
        super(name);
        this.successPolicy = successPolicy;
        this.failurePolicy = failurePolicy;
    }
    
    @Override
    protected BTStatus execute(AIContext context) {
        int successCount = 0;
        int failureCount = 0;
        int runningCount = 0;
        
        // 执行所有子节点
        for (BTNode child : children) {
            BTStatus childStatus = child.tick(context);
            
            switch (childStatus) {
                case SUCCESS:
                    successCount++;
                    break;
                case FAILURE:
                    failureCount++;
                    break;
                case RUNNING:
                    runningCount++;
                    break;
            }
        }
        
        // 检查失败条件
        if (failurePolicy == Policy.ANY_FAILURE && failureCount > 0) {
            return BTStatus.FAILURE;
        }
        if (failurePolicy == Policy.ALL_FAILURE && failureCount == children.size()) {
            return BTStatus.FAILURE;
        }
        
        // 检查成功条件
        if (successPolicy == Policy.ANY_SUCCESS && successCount > 0) {
            return BTStatus.SUCCESS;
        }
        if (successPolicy == Policy.ALL_SUCCESS && successCount == children.size()) {
            return BTStatus.SUCCESS;
        }
        
        // 还有节点在运行
        if (runningCount > 0) {
            return BTStatus.RUNNING;
        }
        
        // 默认返回失败
        return BTStatus.FAILURE;
    }
}
