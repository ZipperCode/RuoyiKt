package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.common.core.domain.mixin.base.LogicDeleteMixin
import org.zipper.common.core.domain.mixin.sys.SysDeptMixin

/**
 * 部门表 sys_dept
 *
 */
@TableName("sys_dept")
class SysDeptEntity : BaseMixinEntity(), SysDeptMixin, LogicDeleteMixin {
    /**
     * 部门ID
     */
    @field:TableId(value = "dept_id")
    override var deptId: Long? = null

    /**
     * 父部门ID
     */
    override var parentId: Long? = null

    /**
     * 部门名称
     */
    override var deptName: String? = null

    /**
     * 显示顺序
     */
    override var orderNum: Int? = null

    /**
     * 负责人
     */
    override var leader: Long? = null

    /**
     * 联系电话
     */
    override var phone: String? = null

    /**
     * 邮箱
     */
    override var email: String? = null

    /**
     * 部门状态:0正常,1停用
     */
    override var status: String? = null

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @field:TableLogic(value = LogicDeleteMixin.NORMAL, delval = LogicDeleteMixin.DELETED)
    override var deleted: String? = null

    /**
     * 祖级列表
     */
    var ancestors: String = ""

}
