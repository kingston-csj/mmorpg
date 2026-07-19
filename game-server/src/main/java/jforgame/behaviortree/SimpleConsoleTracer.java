package jforgame.behaviortree;

/**
 * 简单控制台输出的 Tracer，实现节点级进入/退出打印
 */
public class SimpleConsoleTracer implements BTTracer {
    @Override
    public void onEnter(BTNode node, AIContext context) {
        System.out.println("[AI TRACE] -> enter | node=" + node.getClass().getSimpleName() + 
                "(" + node.getName() + ")");
    }

    @Override
    public void onExit(BTNode node, AIContext context, BTStatus status, long durationNanos) {
        System.out.println("[AI TRACE] <- exit  | node=" + node.getClass().getSimpleName() + 
                "(" + node.getName() + ")" +
                " | status=" + status +
                " | durationMs=" + String.format("%.3f", durationNanos / 1_000_000.0));
    }
}


