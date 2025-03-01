package org.zipper.modules.account.mapper

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.toolkit.Constants
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.zipper.modules.account.domain.entity.AppAccountEntity
import org.zipper.modules.account.domain.vo.AppAccountVo

@Mapper
interface AppAccountMapper : BaseMapper<AppAccountEntity> {
    fun selectAccountList(
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<AppAccountEntity>
    ): List<AppAccountVo>

    fun selectAppAccountList(
        @Param("page") page: Page<AppAccountEntity>,
        @Param(Constants.WRAPPER) queryWrapper: Wrapper<AppAccountEntity>
    ): List<AppAccountVo>
}