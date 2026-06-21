package org.forfun.mmorpg.game.skill.util

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * 平面直角坐标系下的角度相关计算工具类
 */
object AngleUtil {

    const val CIRCLE_ANGLE = 360

    const val HALF_CIRCLE_ANGLE = 180

    /**
     * 返回坐标点于坐标系原点连线，相对平面坐标系的角度
     *
     * @param x 横轴坐标
     * @param y 纵轴坐标
     * @return 角度范围：(-180，180]
     */
    fun relativePosition2Angle(x: Double, y: Double): Double {
        return Math.toDegrees(Math.atan2(y, x))
    }

    /**
     * 计算目标坐标点到起点坐标点连线，相对平面坐标系的角度
     *
     * @param sourceX 起点横轴坐标
     * @param sourceY 起点纵轴坐标
     * @param targetX 目标点横轴坐标
     * @param targetY 目标点纵轴坐标
     * @return 角度范围：[0,360)
     */
    fun position2Angle(sourceX: Double, sourceY: Double, targetX: Double, targetY: Double): Double {
        return circleAngle(relativePosition2Angle(targetX - sourceX, targetY - sourceY))
    }

    /**
     * 计算目标坐标点是否包含在起点（圆心）坐标指定半径的圆内
     */
    fun isInCircle(sourceX: Double, sourceY: Double, targetX: Double, targetY: Double, radius: Double): Boolean {
        checkNotNegative(radius, "圆半径")
        return square(radius) >= square(targetX - sourceX) + square(targetY - sourceY)
    }

    /**
     * 计算目标坐标点是否包含在起点（圆心）坐标的指定范围的环内
     */
    fun isInRing(
        sourceX: Double,
        sourceY: Double,
        targetX: Double,
        targetY: Double,
        innerRadius: Double,
        outerRadius: Double
    ): Boolean {
        checkNotNegative(innerRadius, "内圆半径")
        checkNotNegative(outerRadius, "外圆半径")

        val inner = if (innerRadius <= outerRadius) innerRadius else outerRadius
        val outer = if (inner == innerRadius) outerRadius else innerRadius
        val distanceSquare = square(targetX - sourceX) + square(targetY - sourceY)
        return square(inner) <= distanceSquare && distanceSquare <= square(outer)
    }

    /**
     * 计算目标坐标点是否在起点（扇形的圆心）坐标点指定方向的扇形中
     */
    fun isInSector(
        sourceX: Double,
        sourceY: Double,
        targetX: Double,
        targetY: Double,
        radius: Double,
        axisAngle: Double,
        extendAngle: Double
    ): Boolean {
        checkNotNegative(radius, "扇形半径")

        if (extendAngle >= HALF_CIRCLE_ANGLE) {
            return isInCircle(sourceX, sourceY, targetX, targetY, radius)
        }

        val radiusSquare = square(radius)
        val distanceSquare = square(targetX - sourceX) + square(targetY - sourceY)
        if (distanceSquare <= radiusSquare) {
            val maxAngle = circleAngle(axisAngle + extendAngle)
            val minAngle = circleAngle(axisAngle - extendAngle)
            val realAngel = position2Angle(sourceX, sourceY, targetX, targetY)

            val across = minAngle > maxAngle
            return (maxAngle >= realAngel || across) && (realAngel >= minAngle || across)
        }
        return false
    }

    /**
     * 计算目标坐标点是否在指定起点（该起点位于矩形宽度边的中心点上）的矩形范围内
     */
    fun isInRect(
        sourceX: Double,
        sourceY: Double,
        targetX: Double,
        targetY: Double,
        axisAngle: Double,
        width: Double,
        length: Double
    ): Boolean {
        checkNotNegative(length, "矩形长度")
        checkNotNegative(width, "矩形宽度")

        if (sourceX == targetX && sourceY == targetY) {
            return true
        } else if (length == 0.0 || width == 0.0) {
            return false
        }

        // 计算目标点相对起点的距离
        val dist = distance(sourceX, sourceY, targetX, targetY)

        val targetAngle = position2Angle(sourceX, sourceY, targetX, targetY)
        val relativeAngle = circleAngle(targetAngle - axisAngle)

        // 计算目标坐标点在新坐标系中X轴（横轴）中的相对坐标
        val targetRelativeX = angleToXRatio(relativeAngle) * dist
        // 计算目标坐标点在新坐标系中Y轴（纵轴）中的相对坐标
        val targetRelativeY = angleToYRatio(relativeAngle) * dist

        val halfWid = width / 2.0
        return 0.0 <= targetRelativeX && targetRelativeX <= length && -halfWid <= targetRelativeY && targetRelativeY <= halfWid
    }

    /**
     * 将传入角度转换为坐标系角度[0,360)
     */
    private fun circleAngle(angle: Double): Double {
        val a = if (angle < 0) angle + CIRCLE_ANGLE else angle
        return a % CIRCLE_ANGLE
    }

    /**
     * 计算两点的距离
     */
    fun distance(sourceX: Double, sourceY: Double, targetX: Double, targetY: Double): Double {
        return sqrt(square(targetX - sourceX) + square(targetY - sourceY))
    }

    /**
     * 计算指定角度在Y轴上的距离计算比例
     */
    fun angleToYRatio(angle: Double): Double {
        val a = circleAngle(angle)
        return when {
            a == 0.0 -> 0.0
            a % 90.0 == 0.0 -> when (a) {
                180.0 -> 0.0
                270.0 -> -1.0
                else -> 1.0
            }
            else -> Math.sin(Math.toRadians(a))
        }
    }

    /**
     * 计算指定角度在X轴上的距离计算比例
     */
    fun angleToXRatio(angle: Double): Double {
        val a = circleAngle(angle)
        return when {
            a == 0.0 -> 1.0
            a % 90.0 == 0.0 -> when (a) {
                180.0 -> -1.0
                else -> 0.0
            }
            else -> Math.cos(Math.toRadians(a))
        }
    }

    /**
     * 求平方
     */
    fun square(num: Double): Double = num.pow(2)

    private fun checkNotNegative(num: Double, errorMessage: String) {
        if (num < 0) {
            throw IllegalArgumentException("[$errorMessage]不能小于0")
        }
    }
}
