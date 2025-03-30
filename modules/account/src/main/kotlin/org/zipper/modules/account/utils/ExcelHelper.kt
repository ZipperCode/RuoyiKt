package org.zipper.modules.account.utils

import com.alibaba.excel.EasyExcel
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy
import jakarta.servlet.http.HttpServletResponse
import org.zipper.common.core.ext.log
import org.zipper.framework.mybatis.core.page.PageQuery
import java.net.URLEncoder

object ExcelHelper {

    inline fun <reified Export> HttpServletResponse.writeToExcel(
        sheetName: String,
        query: (PageQuery) -> List<Export>
    ) {
        try {
            contentType = "application/vnd.ms-excel"
            characterEncoding = "utf-8"
            val fileName = URLEncoder.encode(sheetName, "UTF-8")
            setHeader("Content-disposition", "attachment;filename=$fileName.xlsx")
            val pageSize = 1000 // 每次查询的数据量
            var currentPage = 1
            var hasMoreData = true

            val excelWriter = EasyExcel.write(outputStream, Export::class.java)
                .registerWriteHandler(LongestMatchColumnWidthStyleStrategy())
                .build()
            val writeSheet = EasyExcel.writerSheet(sheetName)
                .registerWriteHandler(LongestMatchColumnWidthStyleStrategy())
                .build()

            while (hasMoreData) {
                val pageQuery = PageQuery()
                pageQuery.pageNum = currentPage
                pageQuery.pageSize = pageSize
                val dataList = query(pageQuery)

                if (dataList.isNotEmpty()) {
                    excelWriter.write(dataList, writeSheet)
                    currentPage++
                    if (dataList.size < pageSize) {
                        hasMoreData = false
                    }
                } else {
                    hasMoreData = false
                }
            }
            excelWriter.finish()
        } catch (e: Exception) {
            log.error("writeToExcel 失败", e)
        }
    }
}