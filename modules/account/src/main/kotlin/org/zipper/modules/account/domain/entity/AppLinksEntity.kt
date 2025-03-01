package org.zipper.modules.account.domain.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.common.core.domain.mixin.account.AppLinksMixin

@TableName("app_links")
class AppLinksEntity : BaseMixinEntity(), AppLinksMixin {
    @field:TableId(value = "id", type = IdType.AUTO)
    override var id: Long? = null
    override var link: String? = null
    override var classify: Int? = null
    override var remark: String? = null
}