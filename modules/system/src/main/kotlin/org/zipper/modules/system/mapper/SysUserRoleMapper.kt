package org.zipper.modules.system.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.zipper.modules.system.domain.entity.SysUserRoleEntity


/**
 * 用户与角色关联表 数据层
 *
 * @author Lion Li
 */
interface SysUserRoleMapper : BaseMapper<SysUserRoleEntity> {
    fun selectUserIdsByRoleId(roleId: Long): List<Long>
}
