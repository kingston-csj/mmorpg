package jforgame.behaviortree;

import lombok.extern.slf4j.Slf4j;

/**
 * 行为树核心类
 * 管理AI实体的行为树执行
 * 每次tick执行的时候，总会从根节点开始执行
 */
@Slf4j
public class BehaviorTree {

    /**
     * 根节点
     */
    private final jforgame.behaviortree.BTNode rootNode;

    /**
     * 行为树ID
     */
    private final String treeId;

    /**
     * 行为树名称
     */
    private final String treeName;
    /**
     * 当前执行状态
     */
    private jforgame.behaviortree.BTStatus currentStatus = jforgame.behaviortree.BTStatus.READY;
    /**
     * 轻量级执行跟踪器
     */
    private jforgame.behaviortree.BTTracer tracer;

    /**
     * 是否启用
     */
    private boolean enabled = true;

    public BehaviorTree(String treeId, String treeName, jforgame.behaviortree.BTNode rootNode) {
        this.treeId = treeId;
        this.treeName = treeName;
        this.rootNode = rootNode;
    }

    /**
     * 执行行为树
     *
     * @param context AI上下文
     * @return 执行状态
     */
    public jforgame.behaviortree.BTStatus tick(jforgame.behaviortree.AIContext context) {
        if (!enabled) {
            return jforgame.behaviortree.BTStatus.FAILURE;
        }

        try {
            context.setBehaviorTree(this);
            // 执行根节点
            currentStatus = rootNode.tick(context);

            // 记录执行日志
            if (log.isDebugEnabled()) {
                log.debug("BehaviorTree[{}] tick result: {}", treeName, currentStatus);
            }

            return currentStatus;
        } catch (Exception e) {
            log.error("BehaviorTree[{}] execution error", treeName, e);
            currentStatus = jforgame.behaviortree.BTStatus.FAILURE;
            return currentStatus;
        }
    }

    /**
     * 重置行为树状态
     */
    public void reset() {
        currentStatus = jforgame.behaviortree.BTStatus.READY;
        rootNode.reset();
    }

    // Getters
    public String getTreeId() {
        return treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public jforgame.behaviortree.BTStatus getCurrentStatus() {
        return currentStatus;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取根节点
     */
    public jforgame.behaviortree.BTNode getRootNode() {
        return rootNode;
    }

    /**
     * 设置/获取 tracer
     */
    public void setTracer(jforgame.behaviortree.BTTracer tracer) {
        this.tracer = tracer;
    }

    public jforgame.behaviortree.BTTracer getTracer() {
        return tracer;
    }
}
