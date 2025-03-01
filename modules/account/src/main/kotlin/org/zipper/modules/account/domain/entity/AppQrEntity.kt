package org.zipper.modules.account.domain.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.common.core.domain.mixin.account.AppQrMixin
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.modules.account.constant.AccountType
import org.zipper.modules.account.constant.DataClassify
import org.zipper.modules.account.constant.DataStatus

@TableName("app_account")
open class AppQrEntity : BaseMixinEntity(), AppQrMixin {
    @field:TableId(value = "id", type = IdType.AUTO)
    override var id: Long? = null
    override var qrContent: String? = null
    override var qrPath: String? = null
    override var country: String? = null
    override var work: String? = null
    override var income: Float? = null
    override var age: Int? = 0
    override var classify: Int? = DataClassify.Unknown.classify
    override var status: Int? = DataStatus.Normal.status
    override var remark: String? = null
    override var screenshot: String? = null
    override var modifier: String? = null
}