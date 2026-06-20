package org.forfun.mmorpg.game.util.dirtywords

import org.apache.commons.lang3.StringUtils
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet

class DirtyWordsReader () {

    private val ENCODING = "UTF-8"
    private val dirtyWords: MutableMap<Char, MutableList<String>> = HashMap()
    private var wordCount: Int = 0

    init {
        val words = readSensitiveWordPool()
        initWordsStore(words)
    }

    @Throws(IOException::class)
    private fun readSensitiveWordPool(): Set<String> {
        val words: MutableSet<String> = HashSet()
        val resource: Resource = FileSystemResource("config/SensitiveWord.txt")
        val file = resource.file
        InputStreamReader(FileInputStream(file), ENCODING).use { read ->
            BufferedReader(read).use { bufferedReader ->
                if (file.isFile && file.exists()) {
                    var word: String? = bufferedReader.readLine()
                    while (word != null) {
                        if (StringUtils.isEmpty(word)) {
                            word = bufferedReader.readLine()
                            continue
                        }
                        wordCount++
                        words.add(word.trim { it <= ' ' })
                        word = bufferedReader.readLine()
                    }
                } else {
                    throw FileNotFoundException("dirty words file is empty")
                }
            }
        }
        return words
    }

    private fun initWordsStore(words: Set<String>) {
        for (word in words) {
            if (StringUtils.isEmpty(word)) {
                continue
            }
            val first = word[0]
            val sameFirst = dirtyWords.computeIfAbsent(first) { ArrayList() }
            sameFirst.add(word)
        }
    }

    fun getDirtyWords(): Map<Char, MutableList<String>> {
        return dirtyWords
    }

    fun getWordCount(): Int {
        return wordCount
    }

    companion object {
        @Throws(IOException::class)
        fun create(): DirtyWordsReader {
            return DirtyWordsReader()
        }
    }
}
