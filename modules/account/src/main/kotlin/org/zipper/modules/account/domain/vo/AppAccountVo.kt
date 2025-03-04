package org.zipper.modules.account.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.common.core.domain.mixin.account.AppAccountMixin
import org.zipper.common.core.domain.mixin.sys.SysUserMixin
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.account.constant.DataClassify
import org.zipper.modules.account.constant.DataStatus
import org.zipper.modules.account.domain.entity.AppAccountEntity

@ExcelIgnoreUnannotated
@AutoMapper(target = AppAccountEntity::class, reverseConvertGenerate = true)
open class AppAccountVo : BaseMixinVo(), AppAccountMixin {
    override var id: Long? = null
    override var account: String? = null
    override var country: String? = null
    override var work: String? = null
    override var income: Float? = null
    override var age: Int? = 0
    override var classify: Int? = DataClassify.Unknown.classify
    override var status: Int? = DataStatus.Normal.status
    override var remark: String? = null
    override var linkRemark: String? = null
    override var screenshot: String? = null
    override var modifier: String? = null
    var used: Int? = null

    /**
     * 创建用户名
     */
    var createUser: String? = null
    var record: AppAccountRecordVo? = null

    fun collectUserIds(selectUserIds: MutableList<Long>) {
        this.let {
            if (it.createBy != null) {
                selectUserIds.add(it.createBy!!)
            }
            it.record?.run {
                if (createBy != null) {
                    selectUserIds.add(createBy!!)
                }
                if (bindUserId != null) {
                    selectUserIds.add(bindUserId!!)
                }
            }
        }
    }

    fun fillUser(userMap: Map<Long?, SysUserMixin>) {
        this.let {
            it.createUser = userMap[it.createBy]?.userName
            it.record?.run {
                createUser = userMap[createBy]?.userName
                bindUser = userMap[bindUserId]?.userName
            }
        }
    }
}