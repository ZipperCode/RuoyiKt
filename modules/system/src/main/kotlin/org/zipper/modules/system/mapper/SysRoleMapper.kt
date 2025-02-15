package org.zipper.modules.system.mapper

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param
import org.zipper.framework.mybatis.annotation.DataColumn
import org.zipper.framework.mybatis.annotation.DataPermission
import org.zipper.modules.system.domain.entity.SysRoleEntity
import org.zipper.modules.system.domain.vo.SysRoleVo

/**
 * 角色表 数据层
 *
 * @author Lion Li
 */
interface SysRoleMapper : BaseMapper<SysRoleEntity> {
    @DataPermission(
        DataColumn(key = ["deptName"], value = ["d.dept_id"]),
        DataColumn(key = ["userName"], value = ["r.create_by"])
    )
    fun selectPageRoleList(
        @Param("page") page: Page<SysRoleEntity>, @Param(
            Constants.WRAPPER
        ) queryWrapper: Wrapper<SysRoleEntity>
    ): Page<SysRoleVo>

    /**
     * 根据条件分页查询角色数据
     *
     * @param queryWrapper 查询条件
     * @return 角色数据集合信息
     */
    @DataPermission(
        DataColumn(key = ["deptName"], value = ["d.dept_id"]),
        DataColumn(key = ["userName"], value = ["r.create_by"])
    )
    fun selectRoleList(
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<SysRoleEntity>
    ): List<SysRoleVo>

    @DataPermission(
        DataColumn(key = ["deptName"], value = ["d.dept_id"]),
        DataColumn(key = ["userName"], value = ["r.create_by"])
    )
    fun selectRoleById(roleId: Long?): SysRoleVo?

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    fun selectRolePermissionByUserId(userId: Long?): List<SysRoleVo>


    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    fun selectRoleListByUserId(userId: Long?): List<Long>

    /**
     * 根据用户ID查询角色
     *
     * @param userName 用户名
     * @return 角色列表
     */
    fun selectRolesByUserName(userName: String?): List<SysRoleVo>
}
