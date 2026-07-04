package org.forfun.mmorpg.game.gm

object GmDoc {

    fun printDoc(): String {
        val sb = StringBuilder()
        sb.append("--------gm命令说明-----------\n")
        for (cmd in GmCommands.entries) {
            sb.append(cmd.desc + "\t-->\t(" + cmd.example).append(")\n")
        }

        return sb.toString()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val doc = printDoc()
        println(doc)
    }
}
