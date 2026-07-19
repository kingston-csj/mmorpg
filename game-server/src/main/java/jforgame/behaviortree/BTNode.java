package jforgame.behaviortree;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 行为树节点基类
 * 所有行为树节点都需要继承此类
 * 
 * 
 */
@Slf4j
public abstract class BTNode {
    
    /** 节点名称 */
    protected String name;
    
    /** 父节点 */
    protected BTNode parent;
    
    /** 子节点列表 */
    protected List<BTNode> children = new ArrayList<>();
    
    /** 当前执行状态 */
    protected BTStatus status = BTStatus.READY;
    
    /** 节点参数 */
    protected String parameters;
    
    public BTNode(String name) {
        this.name = name;
    }
    
    /**
     * 执行节点逻辑
     * @param context AI上下文
     * @return 执行状态
     */
    public final BTStatus tick(AIContext context) {
        BehaviorTree tree = context.getBehaviorTree() instanceof BehaviorTree ? (BehaviorTree) context.getBehaviorTree() : null;
        BTTracer tracer = tree != null ? tree.getTracer() : null;
        long startNanos = 0L;
        if (tracer != null) {
            tracer.onEnter(this, context);
            startNanos = System.nanoTime();
        }

        if (status == BTStatus.READY) {
            onStart(context);
        }

        status = execute(context);

        if (status.isTerminal()) {
            onEnd(context);
        }

        if (tracer != null) {
            long duration = System.nanoTime() - startNanos;
            tracer.onExit(this, context, status, duration);
        }

        return status;
    }
    
    /**
     * 节点开始执行时的回调
     */
    protected void onStart(AIContext context) {
        if (log.isDebugEnabled()) {
            log.debug("BTNode[{}] onStart", name);
        }
    }
    
    /**
     * 节点结束执行时的回调
     */
    protected void onEnd(AIContext context) {
        if (log.isDebugEnabled()) {
            log.debug("BTNode[{}] onEnd, status: {}", name, status);
        }
    }
    
    /**
     * 子类需要实现的具体执行逻辑
     */
    protected abstract BTStatus execute(AIContext context);
    
    /**
     * 重置节点状态
     */
    public void reset() {
        status = BTStatus.READY;
        for (BTNode child : children) {
            child.reset();
        }
    }
    
    /**
     * 添加子节点
     */
    public void addChild(BTNode child) {
        if (child != null) {
            child.parent = this;
            children.add(child);
        }
    }
    
    /**
     * 移除子节点
     */
    public void removeChild(BTNode child) {
        if (child != null) {
            child.parent = null;
            children.remove(child);
        }
    }
    
    /**
     * 获取指定索引的子节点
     */
    public BTNode getChild(int index) {
        if (index >= 0 && index < children.size()) {
            return children.get(index);
        }
        return null;
    }
    
    /**
     * 获取子节点数量
     */
    public int getChildCount() {
        return children.size();
    }
    
    /**
     * 检查是否有子节点
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public BTNode getParent() {
        return parent;
    }
    
    public void setParent(BTNode parent) {
        this.parent = parent;
    }

    public List<BTNode> getChildren() {
        return children;
    }
    
    public BTStatus getStatus() {
        return status;
    }
    
    public String getParameters() {
        return parameters;
    }
    
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
