package org.zipper.common.core.utils

import cn.hutool.core.io.FileMagicNumber
import cn.hutool.core.io.FileUtil
import cn.hutool.core.io.IoUtil
import java.io.BufferedInputStream
import java.io.InputStream

object FileUtils {

    /**
     * 简单扩展文件类型
     * 后续需要判断精确，可以使用tika库判断
     */
    fun getFileType(inputStream: InputStream, filename: String?, readHeadSize: Int = 64): FileMagicNumber {
        val buffer = BufferedInputStream(inputStream)
        buffer.mark(readHeadSize)
        val magicNumber = IoUtil.readBytes(inputStream, readHeadSize)
        buffer.reset()
        val fileMagic = FileMagicNumber.getMagicNumber(magicNumber)
        return validFileType(fileMagic, filename)
    }

    fun getFileType(bytes: ByteArray, filename: String?): FileMagicNumber {
        val fileMagic = FileMagicNumber.getMagicNumber(bytes)
        return validFileType(fileMagic, filename)
    }

    fun validFileType(fileMagic: FileMagicNumber, filename: String?): FileMagicNumber {
        if (fileMagic.extension == "zip" || fileMagic.extension == "jar") {
            val extName = FileUtil.extName(filename)
            when (extName) {
                "docx" -> return FileMagicNumber.DOCX
                "xlsx" -> return FileMagicNumber.XLSX
                "pptx" -> return FileMagicNumber.PPTX
            }
        }
        return fileMagic
    }
}