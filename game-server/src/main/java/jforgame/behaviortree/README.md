# 行为树 AI 工具

这是一个基于行为树的 AI 工具，专为 Java 游戏服务器设计，提供了完整的 AI 行为管理功能。

## 特性

- **完整的行为树框架**：支持复合节点、装饰器节点、条件节点和动作节点
- **可重用的节点类型**：提供多种预定义的节点类型，满足大部分 AI 需求
- **黑板系统**：支持 AI 实体间的数据共享和状态管理
- **高性能**：使用线程池管理 AI 执行，支持大量 AI 实体
- **易于扩展**：提供清晰的接口，方便自定义节点类型
- **Spring 集成**：完全集成到 Spring 框架中，支持依赖注入

## 核心组件

### 1. 行为树核心类

- `BehaviorTree`: 行为树主类，管理 AI 执行
- `BTNode`: 行为树节点基类
- `BTStatus`: 节点执行状态枚举
- `AIContext`: AI 执行上下文

### 2. 节点类型

#### 复合节点

- `SequenceNode`: 顺序节点，所有子节点成功才成功
- `SelectorNode`: 选择节点，任何一个子节点成功就成功
- `ParallelNode`: 并行节点，同时执行所有子节点

#### 装饰器节点

- `InverterNode`: 反转节点，反转子节点结果
- `RepeatNode`: 重复节点，重复执行子节点

#### 条件节点

- `BlackboardCondition`: 黑板条件节点
- `TimeCondition`: 时间条件节点

#### 动作节点

- `LogAction`: 日志动作节点
- `SetBlackboardAction`: 设置黑板数据动作
- `WaitAction`: 等待动作节点

### 3. 管理组件

- `AIManager`: AI 管理器，管理所有 AI 实体
- `AIService`: AI 服务，提供业务逻辑
- `AIFacade`: AI 门面，提供统一 API
- `BehaviorTreeBuilder`: 行为树构建器，提供链式 API

## 使用方法

### 1. 基本使用

```java
// 注入AI门面
@Autowired
private AIFacade aiFacade;

// 为NPC创建AI
AIEntity npcAI = aiFacade.createNPC(1001, "simple_ai");

// 设置AI数据
aiFacade.setAIData(1001, "hasTarget", true);
aiFacade.setAIData(1001, "targetId", 2001);

// 手动触发AI执行
aiFacade.triggerAI(1001);
```

### 2. 使用构建器创建行为树

```java
BehaviorTree tree = new BehaviorTreeBuilder()
    .selector("MainSelector")
        .sequence("AttackSequence")
            .blackboardCondition("HasTarget", "hasTarget", true)
            .logAction("AttackLog", "攻击目标!")
            .wait("AttackWait", 1000)
        .end()
        .sequence("PatrolSequence")
            .blackboardCondition("NoTarget", "hasTarget", false)
            .logAction("PatrolLog", "巡逻中...")
            .wait("PatrolWait", 2000)
        .end()
    .end()
    .build("my_ai", "我的AI");
```

### 3. 自定义节点

```java
// 自定义条件节点
public class HealthCondition extends ConditionNode {
    private final double threshold;

    public HealthCondition(String name, double threshold) {
        super(name);
        this.threshold = threshold;
    }

    @Override
    protected boolean checkCondition(AIContext context) {
        Double health = context.getBlackboardData("health", 1.0);
        return health > threshold;
    }
}

// 自定义动作节点
public class MoveAction extends ActionNode {
    private final int targetX, targetY;

    public MoveAction(String name, int targetX, int targetY) {
        super(name);
        this.targetX = targetX;
        this.targetY = targetY;
    }

    @Override
    protected BTStatus performAction(AIContext context) {
        // 执行移动逻辑
        // ...
        return BTStatus.SUCCESS;
    }
}
```

## 预定义 AI 类型

系统提供了多种预定义的 AI 类型：

- `simple_ai`: 简单 AI，包含基本的攻击和巡逻逻辑
- `complex_ai`: 复杂 AI，包含战斗、巡逻、休息等多种行为
- `patrol_ai`: 巡逻 AI，专门用于巡逻行为
- `combat_ai`: 战斗 AI，专门用于战斗行为

## 配置

AI 系统支持以下配置：

```yaml
# application.yml
ai:
  tick-interval: 100 # AI执行间隔（毫秒）
  max-entities: 1000 # 最大AI实体数量
  thread-pool-size: 4 # AI线程池大小
```

## 性能优化

1. **批量操作**：使用批量 API 减少单次操作开销
2. **条件优化**：将最可能失败的条件放在前面
3. **节点复用**：相同的行为树可以创建多个实例
4. **异步执行**：AI 执行不会阻塞主线程

## 扩展指南

### 添加新的节点类型

1. 继承相应的基类（`ConditionNode`、`ActionNode`等）
2. 实现必要的方法
3. 在构建器中添加支持

### 添加新的 AI 类型

1. 在`AIService`中注册新的行为树模板
2. 实现相应的初始化逻辑
3. 提供相应的 API 接口

## 注意事项

1. AI 执行是异步的，不会阻塞游戏主循环
2. 黑板数据是线程安全的，可以在多线程环境中使用
3. 行为树执行状态会在每次 tick 时更新
4. 建议为不同类型的 AI 实体使用不同的行为树模板

## 示例代码

更多示例代码请参考：

- `AIExamples.java`: 各种 AI 行为树示例
- `AITest.java`: 单元测试示例
- `AIFacade.java`: API 使用示例
