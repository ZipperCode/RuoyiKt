package org.zipper.modules.account.utils

import com.alibaba.excel.converters.Converter
import com.alibaba.excel.converters.WriteConverterContext
import com.alibaba.excel.metadata.data.WriteCellData
import org.zipper.modules.account.constant.DataStatus

class DataStatusConverter : Converter<Int> {
    override fun convertToExcelData(context: WriteConverterContext<Int>?): WriteCellData<*> {
        val value = context?.value ?: return WriteCellData<String>("")
        val status = DataStatus.of(value)
        return WriteCellData<String>(status.title)
    }
}