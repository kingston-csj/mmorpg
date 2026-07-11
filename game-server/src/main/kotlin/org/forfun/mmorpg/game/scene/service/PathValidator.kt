package org.forfun.mmorpg.game.scene.service

import org.forfun.mmorpg.game.scene.model.Point
import org.forfun.mmorpg.game.scene.model.Scene

object PathValidator {

    fun isValidPath(scene: Scene, startX: Double, startY: Double, waypoints: List<Point>): Boolean {
        if (waypoints.isEmpty()) {
            return true
        }

        var prevX = startX.toInt()
        var prevY = startY.toInt()

        if (scene.isBlocked(prevX, prevY)) {
            return false
        }

        for (point in waypoints) {
            if (!validateLine(scene, prevX, prevY, point.x.toInt(), point.y.toInt())) {
                return false
            }
            prevX = point.x.toInt()
            prevY = point.y.toInt()
        }

        return true
    }

    private fun validateLine(scene: Scene, x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
        val dx = kotlin.math.abs(x2 - x1)
        val dy = kotlin.math.abs(y2 - y1)
        val sx = if (x1 < x2) 1 else -1
        val sy = if (y1 < y2) 1 else -1

        var x = x1
        var y = y1
        var err = dx - dy

        while (true) {
            if (scene.isBlocked(x, y)) {
                return false
            }

            if (x == x2 && y == y2) {
                break
            }

            val e2 = 2 * err
            if (e2 > -dy) {
                err -= dy
                x += sx
            }
            if (e2 < dx) {
                err += dx
                y += sy
            }
        }

        return true
    }
}