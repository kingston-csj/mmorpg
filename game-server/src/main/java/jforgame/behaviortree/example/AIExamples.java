package jforgame.behaviortree.example;

import jforgame.behaviortree.BehaviorTree;
import jforgame.behaviortree.BehaviorTreeBuilder;
import jforgame.behaviortree.nodes.conditions.TimeCondition;

/**
 * AI示例类
 * 提供各种AI行为树的示例
 */
public class AIExamples {

    /**
     * 创建简单的巡逻AI
     */
    public static BehaviorTree createSimplePatrolAI() {
        return new BehaviorTreeBuilder()
            .sequence("PatrolMain")
                .logAction("StartPatrol", "开始巡逻")
                .repeatForever("PatrolLoop")
                    .sequence("PatrolStep")
                        .logAction("MoveToPoint", "移动到下一个巡逻点")
                        .wait("MoveTime", 2000)
                        .logAction("ReachPoint", "到达巡逻点")
                        .wait("LookAround", 1000)
                    .end()
                .end()
            .end()
            .build("simple_patrol", "简单巡逻AI");
    }

    /**
     * 创建智能巡逻AI（带目标检测）
     */
    public static BehaviorTree createSmartPatrolAI() {
        return new BehaviorTreeBuilder()
            .selector("MainSelector")
                .sequence("CombatSequence")
                    .blackboardCondition("HasTarget", "hasTarget", true)
                    .logAction("EngageTarget", "发现目标，进入战斗状态")
                    .wait("CombatTime", 3000)
                    .setBlackboard("ClearTarget", "hasTarget", false)
                .end()
                .sequence("PatrolSequence")
                    .blackboardCondition("NoTarget", "hasTarget", false)
                    .logAction("ContinuePatrol", "继续巡逻")
                    .wait("PatrolTime", 2000)
                .end()
            .end()
            .build("smart_patrol", "智能巡逻AI");
    }

    /**
     * 创建战斗AI
     */
    public static BehaviorTree createCombatAI() {
        return new BehaviorTreeBuilder()
            .selector("CombatMain")
                .sequence("AttackSequence")
                    .blackboardCondition("HasTarget", "hasTarget", true)
                    .blackboardCondition("InRange", "inRange", true)
                    .blackboardCondition("CanAttack", "canAttack", true)
                    .logAction("AttackTarget", "攻击目标")
                    .wait("AttackTime", 1000)
                    .setBlackboard("AttackCooldown", "canAttack", false)
                .end()
                .sequence("ChaseSequence")
                    .blackboardCondition("HasTarget", "hasTarget", true)
                    .blackboardCondition("NotInRange", "inRange", false)
                    .logAction("ChaseTarget", "追击目标")
                    .wait("ChaseTime", 2000)
                .end()
                .sequence("SearchSequence")
                    .blackboardCondition("NoTarget", "hasTarget", false)
                    .logAction("SearchEnemy", "搜索敌人")
                    .wait("SearchTime", 3000)
                .end()
            .end()
            .build("combat_ai", "战斗AI");
    }

    /**
     * 创建防御AI
     */
    public static BehaviorTree createDefenseAI() {
        return new BehaviorTreeBuilder()
            .selector("DefenseMain")
                .sequence("DefendSequence")
                    .blackboardCondition("UnderAttack", "underAttack", true)
                    .logAction("DefendAction", "进行防御")
                    .wait("DefendTime", 2000)
                    .setBlackboard("DefendComplete", "underAttack", false)
                .end()
                .sequence("RetreatSequence")
                    .blackboardCondition("LowHealth", "health", 0.3)
                    .logAction("RetreatAction", "血量过低，撤退")
                    .wait("RetreatTime", 3000)
                .end()
                .sequence("IdleSequence")
                    .blackboardCondition("Safe", "underAttack", false)
                    .blackboardCondition("Healthy", "health", 0.3)
                    .logAction("IdleAction", "安全状态，待机")
                    .wait("IdleTime", 5000)
                .end()
            .end()
            .build("defense_ai", "防御AI");
    }

    /**
     * 创建采集AI
     */
    public static BehaviorTree createGatherAI() {
        return new BehaviorTreeBuilder()
            .sequence("GatherMain")
                .logAction("StartGather", "开始采集任务")
                .repeat("GatherLoop", 5)
                    .sequence("GatherStep")
                        .blackboardCondition("HasResource", "hasResource", true)
                        .logAction("GatherResource", "采集资源")
                        .wait("GatherTime", 3000)
                        .setBlackboard("ResourceCollected", "hasResource", false)
                    .end()
                .end()
                .logAction("GatherComplete", "采集任务完成")
            .end()
            .build("gather_ai", "采集AI");
    }

    /**
     * 创建复杂AI（综合多种行为）
     */
    public static BehaviorTree createComplexAI() {
        return new BehaviorTreeBuilder()
            .selector("MainSelector")
                .sequence("CombatSequence")
                    .blackboardCondition("InCombat", "inCombat", true)
                    .selector("CombatSelector")
                        .sequence("AttackSequence")
                            .blackboardCondition("CanAttack", "canAttack", true)
                            .logAction("AttackAction", "执行攻击")
                            .wait("AttackCooldown", 1500)
                            .setBlackboard("ResetAttack", "canAttack", false)
                        .end()
                        .sequence("DefendSequence")
                            .blackboardCondition("ShouldDefend", "shouldDefend", true)
                            .logAction("DefendAction", "执行防御")
                            .wait("DefendDuration", 2000)
                        .end()
                    .end()
                .end()
                .sequence("IdleSequence")
                    .blackboardCondition("NotInCombat", "inCombat", false)
                    .selector("IdleSelector")
                        .sequence("PatrolSequence")
                            .timeCondition("PatrolTime", TimeCondition.TimeType.TIME_SINCE_LAST_TICK, 5000)
                            .logAction("PatrolAction", "开始巡逻")
                            .wait("PatrolDuration", 8000)
                        .end()
                        .sequence("RestSequence")
                            .blackboardCondition("LowHealth", "health", 0.3)
                            .logAction("RestAction", "休息恢复血量")
                            .wait("RestDuration", 10000)
                        .end()
                        .sequence("SearchSequence")
                            .logAction("SearchAction", "搜索周围环境")
                            .wait("SearchDuration", 3000)
                        .end()
                    .end()
                .end()
            .end()
            .build("complex_ai", "复杂AI");
    }

    /**
     * 创建宠物AI
     */
    public static BehaviorTree createPetAI() {
        return new BehaviorTreeBuilder()
            .selector("PetMain")
                .sequence("FollowSequence")
                    .blackboardCondition("OwnerNearby", "ownerNearby", true)
                    .logAction("FollowOwner", "跟随主人")
                    .wait("FollowTime", 1000)
                .end()
                .sequence("PlaySequence")
                    .blackboardCondition("OwnerFar", "ownerNearby", false)
                    .logAction("PlayAlone", "独自玩耍")
                    .wait("PlayTime", 5000)
                .end()
                .sequence("RestSequence")
                    .blackboardCondition("Tired", "energy", 0.2)
                    .logAction("RestAction", "休息恢复体力")
                    .wait("RestTime", 8000)
                .end()
            .end()
            .build("pet_ai", "宠物AI");
    }
}
