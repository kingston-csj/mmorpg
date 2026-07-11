package org.forfun.mmorpg.game.scene.service

import org.forfun.mmorpg.game.scene.model.MapBlockData
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory

object MapBlockLoader {

    private val cache = mutableMapOf<String, MapBlockData>()

    fun loadStaticMap(mapId: Int): MapBlockData {
        val path = "map/map_${mapId}.xml"
        return loadFromCache(path)
    }

    fun loadDynamicMap(mapId: Int, blockId: Int): MapBlockData {
        val path = "map/map_${mapId}_dynatic_${blockId}.xml"
        return loadFromCache(path)
    }

    private fun loadFromCache(path: String): MapBlockData {
        return cache.computeIfAbsent(path) { loadXml(path) }
    }

    private fun loadXml(path: String): MapBlockData {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val inputStream = javaClass.classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Map file not found: $path")

        val document: Document = builder.parse(inputStream)
        val root = document.documentElement

        val width = getChildText(root, "width").toInt()
        val height = getChildText(root, "height").toInt()

        val data = MapBlockData(width, height)

        val rowsNodeList = root.getElementsByTagName("rows").item(0) as Element
        val rowNodeList: NodeList = rowsNodeList.getElementsByTagName("row")

        for (i in 0..<rowNodeList.length) {
            val rowText = rowNodeList.item(i).textContent.trim()
            val cells = rowText.split(",").map { it.trim().toInt() }

            for (j in cells.indices) {
                if (cells[j] == 1) {
                    data.setBlock(j, i, true)
                }
            }
        }

        return data
    }

    private fun getChildText(parent: Element, tagName: String): String {
        val nodeList = parent.getElementsByTagName(tagName)
        if (nodeList.length == 0) {
            return "0"
        }
        return nodeList.item(0).textContent.trim()
    }

    fun clearCache() {
        cache.clear()
    }
}