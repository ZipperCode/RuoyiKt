package org.zipper.modules.system.domain.mixin

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableLogic
import org.zipper.framework.mybatis.core.domain.CreatorMixin
import org.zipper.framework.mybatis.core.domain.LogicDeleteMixin
import org.zipper.framework.mybatis.core.domain.UpdaterMixin
import java.io.Serializable

interface SysDeptMixin : CreatorMixin, UpdaterMixin, Serializable {
    /**
     * 部门ID
     */
    var deptId: Long?

    /**
     * 父部门ID
     */
    var parentId: Long?

    /**
     * 部门名称
     */
    var deptName: String?

    /**
     * 显示顺序
     */
    var orderNum: Int?

    /**
     * 负责人
     */
    var leader: Long?

    /**
     * 联系电话
     */
    var phone: String?

    /**
     * 邮箱
     */
    var email: String?

    /**
     * 部门状态:0正常,1停用
     */
    var status: String?
}