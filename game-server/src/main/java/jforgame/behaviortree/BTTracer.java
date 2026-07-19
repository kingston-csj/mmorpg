package jforgame.behaviortree;

/**
 * 轻量级行为树跟踪接口
 * 提供节点进入/退出的钩子，便于打印执行轨迹
 */
public interface BTTracer {

    /**
     * 节点进入时回调
     */
    void onEnter(BTNode node, AIContext context);

    /**
     * 节点退出时回调
     * @param status 节点返回状态
     * @param durationNanos 本次执行耗时（纳秒）
     */
    void onExit(BTNode node, AIContext context, BTStatus status, long durationNanos);
}


