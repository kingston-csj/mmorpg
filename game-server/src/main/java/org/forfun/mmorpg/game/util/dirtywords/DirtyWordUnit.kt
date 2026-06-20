package org.forfun.mmorpg.game.util.dirtywords

import java.util.ArrayList

class DirtyWordUnit(private val source: String, word: String) {

    private val keyWord: String = word
    private val indexList: MutableList<Int> = ArrayList()

    fun checkWordIndex() {
        var index = 0
        if (source.length < keyWord.length) {
            return
        }
        var i = 0
        val n = source.length
        while (i < n) {
            if (keyWord.length > index && source[i] == keyWord[index]) {
                indexList.add(index)
                index++
                if (foundDirtyWord()) {
                    break
                }
            }
            i++
        }
    }

    fun foundDirtyWord(): Boolean {
        return this.indexList.size == keyWord.length
    }

    fun getSource(): String {
        return source
    }

    fun getKeyWord(): String {
        return keyWord
    }

    fun getIndexList(): List<Int> {
        return indexList
    }
}
