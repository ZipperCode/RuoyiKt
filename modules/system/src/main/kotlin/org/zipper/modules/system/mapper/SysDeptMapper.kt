package org.zipper.modules.system.mapper

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.toolkit.Constants
import org.apache.ibatis.annotations.Param
import org.zipper.framework.mybatis.annotation.DataColumn
import org.zipper.framework.mybatis.annotation.DataPermission
import org.zipper.modules.system.domain.entity.SysDeptEntity
import org.zipper.modules.system.domain.vo.SysDeptVo

/**
 * 部门管理 数据层
 *
 * @author Lion Li
 */
interface SysDeptMapper : BaseMapper<SysDeptEntity> {
    /**
     * 查询部门管理数据
     *
     * @param queryWrapper 查询条件
     * @return 部门信息集合
     */
    @DataPermission(DataColumn(key = ["deptName"], value = ["dept_id"]))
    fun selectDeptList(
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<SysDeptEntity>
    ): List<SysDeptVo>

    @DataPermission(DataColumn(key = ["deptName"], value = ["dept_id"]))
    fun selectDeptById(deptId: Long): SysDeptVo?

    /**
     * 根据角色ID查询部门树信息
     *
     * @param roleId            角色ID
     * @param deptCheckStrictly 部门树选择项是否关联显示
     * @return 选中部门列表
     */
    fun selectDeptListByRoleId(
        @Param("roleId") roleId: Long?,
        @Param("deptCheckStrictly") deptCheckStrictly: Boolean?
    ): List<Long>
}
