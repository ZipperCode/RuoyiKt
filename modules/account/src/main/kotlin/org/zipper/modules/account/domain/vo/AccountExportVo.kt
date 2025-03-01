package org.zipper.modules.account.domain.vo

import com.alibaba.excel.annotation.ExcelIgnore
import com.alibaba.excel.annotation.ExcelProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.common.core.domain.mixin.account.AppAccountMixin
import org.zipper.framework.excel.annotation.ExcelDictFormat
import org.zipper.framework.excel.convert.ExcelDictConvert

@AutoMapper(target = AppAccountVo::class, convertGenerate = false)
open class AccountExportVo : AppAccountMixin {

    @field:ExcelProperty(value = ["编号"])
    override var id: Long? = null

    @field:ExcelProperty(value = ["账号"])
    override var account: String? = null

    @field:ExcelProperty(value = ["国家"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(dictType = "app_account_country")
    override var country: String? = null

    @field:ExcelProperty(value = ["工作"])
    override var work: String? = null

    @field:ExcelProperty(value = ["收入"])
    override var income: Float? = null

    @field:ExcelProperty(value = ["年龄"])
    override var age: Int? = null

    @field:ExcelProperty(value = ["类别"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(dictType = "app_account_classify")
    override var classify: Int? = null

    @field:ExcelProperty(value = ["数据状态"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(dictType = "app_account_status")
    override var status: Int? = null

    @field:ExcelProperty(value = ["备注"])
    override var remark: String? = null

    @field:ExcelProperty(value = ["链接备注"])
    override var linkRemark: String? = null

    @field:ExcelIgnore
    override var screenshot: String? = null

    @field:ExcelIgnore
    override var modifier: String? = null
}