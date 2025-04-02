package org.zipper.modules.account.domain.export

import com.alibaba.excel.annotation.ExcelIgnore
import com.alibaba.excel.annotation.ExcelProperty
import org.zipper.common.core.domain.mixin.account.AppAccountMixin
import org.zipper.modules.account.constant.DataClassify
import org.zipper.modules.account.constant.DataStatus
import org.zipper.modules.account.utils.DataStatusConverter

class AppAccountExport: AppAccountMixin {
    @field:ExcelProperty(value = ["序号"])
    override var id: Long? = null
    @field:ExcelProperty(value = ["链接"])
    override var account: String? = null
    @field:ExcelProperty(value = ["国家"])
    override var country: String? = null
    @field:ExcelProperty(value = ["工作"])
    override var work: String? = null
    @field:ExcelProperty(value = ["收入"])
    override var income: Float? = null
    @field:ExcelProperty(value = ["年龄"])
    override var age: Int? = 0
    @field:ExcelIgnore
    override var classify: Int? = DataClassify.Unknown.classify
    @field:ExcelProperty(value = ["状态"], converter = DataStatusConverter::class)
    override var status: Int? = DataStatus.Normal.status
    @field:ExcelProperty(value = ["备注"])
    override var remark: String? = null
    @field:ExcelProperty(value = ["链接备注"])
    override var linkRemark: String? = null
    @field:ExcelProperty(value = ["截图"])
    override var screenshot: String? = null
    @field:ExcelIgnore
    override var modifier: String? = null
}