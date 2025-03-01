package org.zipper.modules.account.domain.param

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.NotNull
import org.zipper.common.core.domain.mixin.account.AppLinksMixin
import org.zipper.common.core.validate.AddGroup
import org.zipper.common.core.validate.EditGroup
import org.zipper.framework.mybatis.core.domain.BaseMixinParam
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.account.domain.entity.AppLinksEntity

@ExcelIgnoreUnannotated
@AutoMapper(target = AppLinksEntity::class, reverseConvertGenerate = false)
class AppLinksParam : BaseMixinParam(), AppLinksMixin {
    @field:NotNull(message = "id不能为空", groups = [EditGroup::class])
    override var id: Long? = null

    @field:NotNull(message = "链接内容不能为空", groups = [AddGroup::class, EditGroup::class])
    override var link: String? = null

    @field:NotNull(message = "classify不能为空", groups = [AddGroup::class, EditGroup::class])
    override var classify: Int? = null

    override var remark: String? = null
}