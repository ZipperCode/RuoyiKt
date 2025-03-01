package org.zipper.modules.account.domain.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.common.core.domain.mixin.account.AppAccountRecordMixin

@TableName("app_account_record")
open class AppAccountRecordEntity : BaseMixinEntity(), AppAccountRecordMixin {
    @field:TableId(value = "id", type = IdType.AUTO)
    override var id: Long? = null
    override var accountId: Long? = null
    override var bindUserId: Long? = null
    override var classify: Int? = null
    override var used: Int? = 0
    override fun toString(): String {
        return "AppAccountRecordEntity(id=$id, accountId=$accountId, bindUserId=$bindUserId, used=$used)"
    }

}