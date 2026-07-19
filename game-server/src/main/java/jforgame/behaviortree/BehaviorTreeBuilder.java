package jforgame.behaviortree;

import jforgame.behaviortree.nodes.ActionNode;
import jforgame.behaviortree.nodes.CompositeNode;
import jforgame.behaviortree.nodes.ConditionNode;
import jforgame.behaviortree.nodes.DecoratorNode;
import jforgame.behaviortree.nodes.InverterNode;
import jforgame.behaviortree.nodes.ParallelNode;
import jforgame.behaviortree.nodes.RepeatNode;
import jforgame.behaviortree.nodes.SelectorNode;
import jforgame.behaviortree.nodes.SequenceNode;
import jforgame.behaviortree.nodes.actions.SetBlackboardAction;
import jforgame.behaviortree.nodes.actions.WaitAction;
import jforgame.behaviortree.nodes.actions.LogAction;
import jforgame.behaviortree.nodes.conditions.BlackboardCondition;
import jforgame.behaviortree.nodes.conditions.TimeCondition;

/**
 * 行为树构建器
 * 提供链式API来构建行为树
 *
 * 
 */
public class BehaviorTreeBuilder {

    private BTNode rootNode;
    private BTNode currentNode;

    public BehaviorTreeBuilder() {
    }

    /**
     * 创建序列节点
     */
    public BehaviorTreeBuilder sequence(String name) {
        SequenceNode node = new SequenceNode(name);
        addNode(node);
        return this;
    }

    /**
     * 创建选择节点
     */
    public BehaviorTreeBuilder selector(String name) {
        SelectorNode node = new SelectorNode(name);
        addNode(node);
        return this;
    }

    /**
     * 创建并行节点
     */
    public BehaviorTreeBuilder parallel(String name) {
        ParallelNode node = new ParallelNode(name);
        addNode(node);
        return this;
    }

    /**
     * 创建并行节点（带策略）
     */
    public BehaviorTreeBuilder parallel(String name, ParallelNode.Policy successPolicy, ParallelNode.Policy failurePolicy) {
        ParallelNode node = new ParallelNode(name, successPolicy, failurePolicy);
        addNode(node);
        return this;
    }

    /**
     * 创建反转节点
     */
    public BehaviorTreeBuilder inverter(String name) {
        InverterNode node = new InverterNode(name);
        addNode(node);
        return this;
    }

    /**
     * 创建重复节点
     */
    public BehaviorTreeBuilder repeat(String name, int count) {
        RepeatNode node = new RepeatNode(name, count);
        addNode(node);
        return this;
    }

    /**
     * 创建无限重复节点
     */
    public BehaviorTreeBuilder repeatForever(String name) {
        RepeatNode node = new RepeatNode(name, -1);
        addNode(node);
        return this;
    }

    /**
     * 创建条件节点
     */
    public BehaviorTreeBuilder condition(String name, ConditionNode conditionNode) {
        addNode(conditionNode);
        return this;
    }

    /**
     * 创建黑板条件
     */
    public BehaviorTreeBuilder blackboardCondition(String name, String key, Object value) {
        BlackboardCondition condition = new BlackboardCondition(name, key, value);
        addNode(condition);
        return this;
    }

    /**
     * 创建时间条件
     */
    public BehaviorTreeBuilder timeCondition(String name, TimeCondition.TimeType timeType, long timeValue) {
        TimeCondition condition = new TimeCondition(name, timeType, timeValue);
        addNode(condition);
        return this;
    }

    /**
     * 创建动作节点
     */
    public BehaviorTreeBuilder action(String name, ActionNode actionNode) {
        addNode(actionNode);
        return this;
    }

    /**
     * 创建日志动作
     */
    public BehaviorTreeBuilder logAction(String name, String message) {
        LogAction action = new LogAction(name, message);
        addNode(action);
        return this;
    }

    /**
     * 创建日志动作（带级别）
     */
    public BehaviorTreeBuilder logAction(String name, String message, LogAction.LogLevel level) {
        LogAction action = new LogAction(name, message, level);
        addNode(action);
        return this;
    }

    /**
     * 创建设置黑板动作
     */
    public BehaviorTreeBuilder setBlackboard(String name, String key, Object value) {
        SetBlackboardAction action = new SetBlackboardAction(name, key, value);
        addNode(action);
        return this;
    }

    /**
     * 创建等待动作
     */
    public BehaviorTreeBuilder wait(String name, long waitTime) {
        WaitAction action = new WaitAction(name, waitTime);
        addNode(action);
        return this;
    }

    /**
     * 结束当前节点，返回到父节点
     */
    public BehaviorTreeBuilder end() {
        if (currentNode != null && currentNode.getParent() != null) {
            currentNode = currentNode.getParent();
        }
        return this;
    }

    /**
     * 构建行为树
     */
    public BehaviorTree build(String treeId, String treeName) {
        if (rootNode == null) {
            throw new IllegalStateException("No root node defined");
        }
        return new BehaviorTree(treeId, treeName, rootNode);
    }

    /**
     * 添加节点到当前节点
     */
    private void addNode(BTNode node) {
        if (rootNode == null) {
            // 第一个节点作为根节点
            rootNode = node;
            currentNode = node;
        } else {
            // 添加到当前节点
            if (currentNode instanceof CompositeNode compositeNode) {
                compositeNode.addChild(node);
            } else if (currentNode instanceof DecoratorNode decoratorNode) {
                decoratorNode.setChild(node);
            } else {
                // 更新当前节点
                currentNode = node;
            }
        }
    }

    /**
     * 创建简单的AI行为树示例
     */
    public static BehaviorTree createSimpleAI(String treeId, String treeName) {
        return new BehaviorTreeBuilder()
                .selector("MainSelector")
                .sequence("AttackSequence")
                .blackboardCondition("HasTarget", "hasTarget", true)
                .logAction("AttackLog", "Attacking target!")
                .wait("AttackWait", 1000)
                .setBlackboard("ClearTarget", "hasTarget", false)
                .end()
                .sequence("PatrolSequence")
                .blackboardCondition("NoTarget", "hasTarget", false)
                .logAction("PatrolLog", "Patrolling...")
                .wait("PatrolWait", 2000)
                .end()
                .end()
                .build(treeId, treeName);
    }

    /**
     * 创建复杂的AI行为树示例
     */
    public static BehaviorTree createComplexAI(String treeId, String treeName) {
        return new BehaviorTreeBuilder()
                .selector("MainSelector")
                .sequence("CombatSequence")
                .blackboardCondition("InCombat", "inCombat", true)
                .selector("CombatSelector")
                .sequence("AttackSequence")
                .blackboardCondition("CanAttack", "canAttack", true)
                .logAction("AttackAction", "Performing attack!")
                .wait("AttackCooldown", 1500)
                .setBlackboard("ResetAttack", "canAttack", false)
                .end()
                .sequence("DefendSequence")
                .blackboardCondition("ShouldDefend", "shouldDefend", true)
                .logAction("DefendAction", "Defending!")
                .wait("DefendDuration", 2000)
                .end()
                .end()
                .end()
                .sequence("IdleSequence")
                .blackboardCondition("NotInCombat", "inCombat", false)
                .selector("IdleSelector")
                .sequence("PatrolSequence")
                .timeCondition("PatrolTime", TimeCondition.TimeType.TIME_SINCE_LAST_TICK, 3000)
                .logAction("PatrolAction", "Starting patrol...")
                .wait("PatrolDuration", 5000)
                .end()
                .sequence("RestSequence")
                .blackboardCondition("LowHealth", "health", 0.3)
                .logAction("RestAction", "Resting to recover health...")
                .wait("RestDuration", 10000)
                .end()
                .end()
                .end()
                .end()
                .build(treeId, treeName);
    }
}
