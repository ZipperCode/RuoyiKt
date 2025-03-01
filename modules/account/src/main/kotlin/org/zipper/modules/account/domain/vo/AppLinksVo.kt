package org.zipper.modules.account.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import com.alibaba.excel.annotation.ExcelProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.account.domain.entity.AppLinksEntity
import org.zipper.common.core.domain.mixin.account.AppLinksMixin

@ExcelIgnoreUnannotated
@AutoMapper(target = AppLinksEntity::class, reverseConvertGenerate = true)
class AppLinksVo : BaseMixinVo(), AppLinksMixin {
    @field:ExcelProperty(value = ["数据ID"])
    override var id: Long? = null
    @field:ExcelProperty(value = ["链接"])
    override var link: String? = null
    override var classify: Int? = null
    @field:ExcelProperty(value = ["备注"])
    override var remark: String? = null

    var createUser: String? = null
}