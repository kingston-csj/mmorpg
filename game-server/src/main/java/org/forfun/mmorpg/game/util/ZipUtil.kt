package org.forfun.mmorpg.game.util

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Base64
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object ZipUtil {

    private val decoder = Base64.getDecoder()
    private val encoder = Base64.getEncoder()

    @JvmStatic
    fun compress(paramString: String?): String? {
        if (paramString == null)
            return null
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var zipOutputStream: ZipOutputStream? = null
        var arrayOfByte: ByteArray? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            zipOutputStream = ZipOutputStream(byteArrayOutputStream)
            zipOutputStream.putNextEntry(ZipEntry("0"))
            zipOutputStream.write(paramString.toByteArray())
            zipOutputStream.closeEntry()
            arrayOfByte = byteArrayOutputStream.toByteArray()
        } catch (localIOException5: IOException) {
            arrayOfByte = null
        } finally {
            zipOutputStream?.close()
            byteArrayOutputStream?.close()
        }

        return encoder.encodeToString(arrayOfByte)
    }

    @JvmStatic
    fun decompress(source: String?): String? {
        if (source == null)
            return null
        val paramArrayOfByte = decoder.decode(source)
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var byteArrayInputStream: ByteArrayInputStream? = null
        var zipInputStream: ZipInputStream? = null
        var str: String?
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            byteArrayInputStream = ByteArrayInputStream(paramArrayOfByte)
            zipInputStream = ZipInputStream(byteArrayInputStream)
            val localZipEntry = zipInputStream.nextEntry
            val arrayOfByte = ByteArray(1024)
            var i = -1
            while (zipInputStream.read(arrayOfByte).also { i = it } != -1)
                byteArrayOutputStream.write(arrayOfByte, 0, i)
            str = byteArrayOutputStream.toString()
        } catch (localIOException7: IOException) {
            str = null
        } finally {
            zipInputStream?.close()
            byteArrayInputStream?.close()
            byteArrayOutputStream?.close()
        }
        return str
    }
}
