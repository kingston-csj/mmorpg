package jforgame.behaviortree;

/**
 * 行为树节点执行状态枚举
 *
 * 
 */
public enum BTStatus {

    /**
     * 准备状态
     */
    READY,


    /**
     * 运行中
     * 节点正在执行中，尚未完成（任务未结束，需后续 tick 继续处理）
     * 用于处理 “耗时任务”（如你之前提到的 “等待 1 秒”“移动到目标点”），告知父节点 “当前任务未结束，下一次 tick 需继续执行该节点”
     */
    RUNNING,

    /**
     * 成功
     * 节点已完成执行，且执行结果符合预期（任务成功）
     * 用于告知父节点 “当前任务已完成，可继续执行下一个子节点（如顺序节点）” 或 “当前分支逻辑生效（如选择节点）”
     */
    SUCCESS,

    /**
     * 失败
     * 节点已完成执行，且执行结果不符合预期（任务失败）
     * 用于告知父节点 “当前任务失败，需跳过后续子节点（如顺序节点）” 或 “需尝试其他分支（如选择节点）”
     */
    FAILURE;

    /**
     * 判断是否为终止状态
     */
    public boolean isTerminal() {
        return this == SUCCESS || this == FAILURE;
    }

    /**
     * 判断是否为运行状态
     */
    public boolean isRunning() {
        return this == RUNNING;
    }
}
