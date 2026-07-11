package org.forfun.mmorpg.game.scene.model

class MapBlockData(val width: Int, val height: Int) {

    private val blocks = Array(height) { BooleanArray(width) }

    fun isBlock(x: Int, y: Int): Boolean {
        if (x !in 0..<width || y < 0 || y >= height) {
            return true
        }
        return blocks[y][x]
    }

    fun setBlock(x: Int, y: Int, blocked: Boolean) {
        if (x in 0..<width && y in 0..<height) {
            blocks[y][x] = blocked
        }
    }

    fun merge(other: MapBlockData) {
        for (y in 0..<height) {
            for (x in 0..<width) {
                if (other.isBlock(x, y)) {
                    blocks[y][x] = true
                }
            }
        }
    }

    fun unmerge(other: MapBlockData) {
        for (y in 0..<height) {
            for (x in 0..<width) {
                if (other.isBlock(x, y)) {
                    blocks[y][x] = false
                }
            }
        }
    }

    fun copy(): MapBlockData {
        val copy = MapBlockData(width, height)
        for (y in 0..<height) {
            System.arraycopy(blocks[y], 0, copy.blocks[y], 0, width)
        }
        return copy
    }
}