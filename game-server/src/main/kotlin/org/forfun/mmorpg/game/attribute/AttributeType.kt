package org.forfun.mmorpg.game.attribute

enum class AttributeType(
    val id: Int,
    /**
     * 是否万分比属性（true表示万分比，如10000表示100%）
     */
    val percent: Boolean
) {

    /** 基础属性 */
    Strength(1, false),      // 力量，影响物理攻击
    Agility(2, false),       // 敏捷，影响闪避和攻击速度
    Intelligence(3, false),  // 智力，影响魔法攻击
    Vitality(4, false),      // 体质，影响最大生命值
    Spirit(5, false),        // 精神，影响最大魔法值
    Attack(6, false),            // 通用攻击力（兼容旧代码）
    Defense(7, false),       // 通用防御力（兼容旧代码）

    /** 生命与法力 */
    MaxHp(10, false),        // 最大生命值
    CurrHp(11, false),       // 当前生命值
    MaxMp(12, false),        // 最大魔法值
    CurrMp(13, false),       // 当前魔法值

    /** 攻击属性 */
    PhysicalAttack(20, false),     // 物理攻击力
    MagicAttack(21, false),        // 魔法攻击力
    MinPhysicalAttack(22, false),  // 最小物理攻击
    MaxPhysicalAttack(23, false),  // 最大物理攻击
    MinMagicAttack(24, false),     // 最小魔法攻击
    MaxMagicAttack(25, false),     // 最大魔法攻击

    /** 防御属性 */
    PhysicalDefense(30, false),    // 物理防御力
    MagicDefense(31, false),       // 魔法防御力

    /** 暴击属性（万分比） */
    CriticalRate(40, true),        // 暴击率
    CriticalDamage(41, true),      // 暴击伤害加成
    CriticalResist(42, true),      // 暴击抵抗率

    /** 命中与闪避（万分比） */
    HitRate(50, true),             // 命中率
    DodgeRate(51, true),           // 闪避率

    /** 速度属性 */
    MoveSpeed(80, false),          // 移动速度
    AttackSpeed(81, true),         // 攻击速度加成（万分比）

    /** PVP属性 */
    PvpAttack(120, false),         // PVP攻击力
    PvpDefense(121, false),        // PVP防御力

}