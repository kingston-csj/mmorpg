package com.kingston.mmorpg.game.skill.util;

/**
 * 平面直角坐标系下的角度相关计算工具类
 *
 * @author fagarine
 */
public class AngleUtil {
    private AngleUtil() {
    }

    public static final int CIRCLE_ANGLE = 360;

    public static final int HALF_CIRCLE_ANGLE = 180;

    public static void main(String[] args) {
        // 圆形测试
        //        System.out.println(isInCircle(0, 0, 0, 0, 0));
        //        System.out.println(isInCircle(0, 0, 1, 1, 0.5));
        //        System.out.println(isInCircle(0, 0, 1, 1, 1));
        //        System.out.println(isInCircle(0, 0, 1, 1, 1.414));
        //        System.out.println(isInCircle(0, 0, 1, 1, 1.415));

        // 环形测试

        //        System.out.println(isInRing(0, 0, 1, 1, 1.41, 1.414));
        //        System.out.println(isInRing(0, 0, 1, 1, 1.41, 1.415));
        //        System.out.println(isInRing(0, 0, 1, 1, 1.5, 1.4));
        //        System.out.println(isInRing(0, 0, 1, 1, 1.5, 1.42));

        // 扇形测试
        //        System.out.println(isInSector(0, 0, 1, 1, 2, 0, 30));
        //        System.out.println(isInSector(0, 0, 1, 1, 2, 0, 45));
        //        System.out.println(isInSector(0, 0, 1, 1, 2, 0, 50));
        //        System.out.println(isInSector(0, 0, 1, 1, 2, 225, 135));
        //        System.out.println(isInSector(0, 0, 1, 1, 2, 30, 150));
        //        System.out.println(isInSector(0, 0, 1, 1, 2, 30, 180));

        // 矩形测试
        //        System.out.println(isInRect(0, 0, 0, 0, 30, 0, 10));
        //        System.out.println(isInRect(0, 0, 1, 1, 30, 0, 10));
        //        System.out.println(isInRect(0, 0, 1, 1, 30, 0.1, 10));
        //        System.out.println(isInRect(0, 0, 1, 1, 30, 1, 10));
        //        System.out.println(isInRect(0, 0, 1, 1, 30, 10, 1));
        //        System.out.println(isInRect(0, 0, -1, -1, 30, 10, 1));
        //        System.out.println(isInRect(0, 0, -1, -1, 225, 1, 1));
        //        System.out.println(isInRect(0, 0, -1, -1, 225, 1, 1.5));
    }

    /**
     * 返回坐标点于坐标系原点连线，相对平面坐标系的角度
     *
     * @param x 横轴坐标
     * @param y 纵轴坐标
     * @return 角度范围：(-180，180]
     */
    public static double relativePosition2Angle(double x, double y) {
        return Math.toDegrees(Math.atan2(y, x));
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
    public static double position2Angle(double sourceX, double sourceY, double targetX, double targetY) {
        return circleAngle(relativePosition2Angle(targetX - sourceX, targetY - sourceY));
    }

    /**
     * 计算目标坐标点是否包含在起点（圆心）坐标指定半径的圆内
     *
     * @param sourceX 起点横轴坐标
     * @param sourceY 起点纵轴坐标
     * @param targetX 目标点横轴坐标
     * @param targetY 目标点纵轴坐标
     * @param radius 圆半径
     * @return 返回计算结果
     */
    public static boolean isInCircle(double sourceX, double sourceY, double targetX, double targetY, double radius) {
        checkNotNegative(radius, "圆半径");
        return square(radius) >= square(targetX - sourceX) + square(targetY - sourceY);
    }

    /**
     * 计算目标坐标点是否包含在起点（圆心）坐标的指定范围的环内
     *
     * @param sourceX 起点横轴坐标
     * @param sourceY 起点纵轴坐标
     * @param targetX 目标点横轴坐标
     * @param targetY 目标点纵轴坐标
     * @param innerRadius 内圆半径
     * @param outerRadius 外圆半径
     * @return 返回计算结果
     */
    public static boolean isInRing(double sourceX, double sourceY, double targetX, double targetY, double innerRadius,
            double outerRadius) {
        checkNotNegative(innerRadius, "内圆半径");
        checkNotNegative(outerRadius, "外圆半径");

        double inner = innerRadius <= outerRadius ? innerRadius : outerRadius;
        double outer = inner == innerRadius ? outerRadius : innerRadius;
        double distanceSquare = square(targetX - sourceX) + square(targetY - sourceY);
        return square(inner) <= distanceSquare && distanceSquare <= square(outer);
    }

    /**
     * 计算目标坐标点是否在起点（扇形的圆心）坐标点指定方向的扇形中
     *
     * @param sourceX 起点横轴坐标
     * @param sourceY 起点纵轴坐标
     * @param targetX 目标点横轴坐标
     * @param targetY 目标点纵轴坐标
     * @param radius 扇形半径
     * @param axisAngle 扇形朝向，扇形夹角中轴线相对平面坐标系的角度，有效值范围：[0,360)
     * @param extendAngle 扇形以中轴线向两边扩展的角度，有效值范围：[0,180)
     * @return 返回计算结果
     */
    public static boolean isInSector(double sourceX, double sourceY, double targetX, double targetY, double radius,
            double axisAngle, double extendAngle) {
        checkNotNegative(radius, "扇形半径");

        if (extendAngle >= HALF_CIRCLE_ANGLE) {
            return isInCircle(sourceX, sourceY, targetX, targetY, radius);
        }

        double radiusSquare = square(radius);
        double distanceSquare = square(targetX - sourceX) + square(targetY - sourceY);
        if (distanceSquare <= radiusSquare) {
            double maxAngle = circleAngle(axisAngle + extendAngle);
            double minAngle = circleAngle(axisAngle - extendAngle);
            double realAngel = position2Angle(sourceX, sourceY, targetX, targetY);

            boolean across = minAngle > maxAngle;
            return maxAngle >= realAngel && (realAngel >= minAngle || across);
        }
        return false;
    }

    /**
     * 计算目标坐标点是否在指定起点（该起点位于矩形宽度边的中心点上）的矩形范围内
     * <p>
     * 注意：这里的长度边和宽度边，是相对于矩形起点定义的（与实际长度无关），起点所在的边即为宽度边，其垂直边为长度边
     * </p>
     *
     * @param sourceX 起点横轴坐标
     * @param sourceY 起点纵轴坐标
     * @param targetX 目标点横轴坐标
     * @param targetY 目标点纵轴坐标
     * @param axisAngle 矩形朝向，矩形长度边相对平面坐标系的角度，有效值范围：[0,360)
     * @param width 矩形宽度
     * @param length 矩形长度
     * @return 返回计算结果
     */
    public static boolean isInRect(double sourceX, double sourceY, double targetX, double targetY, double axisAngle,
            double width, double length) {
        checkNotNegative(length, "矩形长度");
        checkNotNegative(width, "矩形宽度");

        if ((sourceX == targetX && sourceY == targetY)) {
            return true;
        } else if (length == 0 || width == 0) {
            return false;
        }

        // 计算目标点相对起点的距离
        double distance = distance(sourceX, sourceY, targetX, targetY);

        /*
         * 直接在平面坐标系上计算比较麻烦，这里将矩形和目标点转为一个相对矩形的新坐标系，计算目标点在新坐标系中的坐标，然后对比。
         * 新坐标系的原点为矩形起点，Y轴为矩形的宽度边，X轴为从起点出发，与长度边平行的矩形中轴线
         */
        double targetAngle = position2Angle(sourceX, sourceY, targetX, targetY);
        double relativeAngle = circleAngle(targetAngle - axisAngle);

        // 计算目标坐标点在新坐标系中X轴（横轴）中的相对坐标
        double targetRelativeX = angleToXRatio(relativeAngle) * distance;
        // 计算目标坐标点在新坐标系中Y轴（纵轴）中的相对坐标
        double targetRelativeY = angleToYRatio(relativeAngle) * distance;

        double halfWid = width / 2D;
        return 0 <= targetRelativeX && targetRelativeX <= length && -halfWid <= targetRelativeY
                && targetRelativeY <= halfWid;
    }

    /**
     * 将传入角度转换为坐标系角度[0,360)
     *
     * @param angle 角度
     * @return 角度范围：[0,360)
     */
    private static double circleAngle(double angle) {
        double a = angle < 0 ? angle + CIRCLE_ANGLE : angle;
        return a % CIRCLE_ANGLE;
    }

    /**
     * 计算两点的距离
     *
     * @param sourceX 起点的X坐标
     * @param sourceY 起点的Y坐标
     * @param targetX 终点的X坐标
     * @param targetY 终点的Y坐标
     * @return 返回距离
     */
    public static double distance(double sourceX, double sourceY, double targetX, double targetY) {
        return Math.sqrt(square(targetX - sourceX) + square(targetY - sourceY));
    }

    /**
     * 计算指定角度在Y轴上的距离计算比例
     *
     * @param angle 平面坐标系角度
     * @return 返回Y轴计算比例
     */
    public static double angleToYRatio(double angle) {
        double a = circleAngle(angle);
        if (a == 0) {
            return 0;
        } else if (a % 90 == 0) {
            return a == 180 ? 0 : (a == 270 ? -1 : 1);
        }
        return Math.sin(Math.toRadians(a));
    }

    /**
     * 计算指定角度在X轴上的距离计算比例
     *
     * @param angle 平面坐标系角度
     * @return 返回X轴计算比例
     */
    public static double angleToXRatio(double angle) {
        double a = circleAngle(angle);
        if (a == 0) {
            return 1;
        }
        if (a % 90 == 0) {
            return a == 180 ? -1 : 0;
        }
        return Math.cos(Math.toRadians(a));
    }

    /**
     * 求平方
     *
     * @param num 数值
     * @return 返回传入数值的平方值
     */
    public static double square(double num) {
        return Math.pow(num, 2);
    }

    private static void checkNotNegative(double num, String errorMessage) {
        if (num < 0) {
            throw new IllegalArgumentException("[" + errorMessage + "]不能小于0");
        }
    }
}
