package org.forfun.mmorpg.game.admin

class SimplyReply {

    var status: Int = 0
    var msg: String? = null

    companion object {
        // 简化构造，直接实例返回
        fun succ(msg: String?) = SimplyReply().apply { this.msg = msg }
        fun fail(code: Int, msg: String?) = SimplyReply().apply {
            status = code
            this.msg = msg
        }
    }
}
