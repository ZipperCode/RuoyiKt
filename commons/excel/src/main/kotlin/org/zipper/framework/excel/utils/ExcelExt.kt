package org.zipper.framework.excel.utils

import com.alibaba.excel.write.handler.WriteHandler
import jakarta.servlet.http.HttpServletResponse
import org.zipper.framework.excel.core.ExcelDownHandler
import org.zipper.framework.excel.utils.ExcelUtil.resetResponse
import org.zipper.framework.excel.utils.ExcelUtil.writeToExcel
import java.io.IOException

inline fun <reified T> HttpServletResponse.responseToExcel(
    data: List<T>,
    sheetName: String,
    merge: Boolean = false,
    writeHandler: WriteHandler = ExcelDownHandler(emptyList())
) {
    try {
        resetResponse(sheetName, this)
        outputStream.writeToExcel(data, sheetName, T::class.java, merge, writeHandler)
    } catch (e: IOException) {
        throw RuntimeException("导出Excel异常")
    }
}