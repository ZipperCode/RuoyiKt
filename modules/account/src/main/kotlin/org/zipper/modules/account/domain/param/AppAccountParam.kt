package org.zipper.modules.account.domain.param

import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.zipper.common.core.domain.mixin.account.AppAccountMixin
import org.zipper.common.core.validate.AddGroup
import org.zipper.common.core.validate.EditGroup
import org.zipper.framework.mybatis.core.domain.BaseMixinParam
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.account.constant.DataStatus
import org.zipper.modules.account.domain.entity.AppAccountEntity

@AutoMapper(target = AppAccountEntity::class, reverseConvertGenerate = false)
open class AppAccountParam : BaseMixinParam(), AppAccountMixin {
    @field:NotNull(message = "id不能为空", groups = [EditGroup::class])
    override var id: Long? = null

    @field:NotNull(message = "account不能为空", groups = [AddGroup::class, EditGroup::class])
    override var account: String? = null
    override var country: String? = null
    override var work: String? = null
    override var income: Float? = 0.0f

    @field:Min(0, message = "age不能小于0", groups = [AddGroup::class, EditGroup::class])
    @field:Max(150, message = "age不能大于150", groups = [AddGroup::class, EditGroup::class])
    override var age: Int? = 0

    @field:NotNull(message = "classify不能为空")
    @field:Min(0, message = "classify不能小于0")
    @field:Max(100, message = "classify不能大于100")
    override var classify: Int? = null

    //    @NotNull(message = "status不能为空", groups = [AddGroup::class, EditGroup::class])
    override var status: Int? = null

    //    @Size(max = 100, message = "remark不能超过100个字符", groups = [AddGroup::class, EditGroup::class])
    override var remark: String? = null

    override var linkRemark: String? = null

    override var screenshot: String? = null
    override var modifier: String? = null


    var dispatchAll: Boolean? = false
}