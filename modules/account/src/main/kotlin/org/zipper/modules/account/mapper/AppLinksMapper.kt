package org.zipper.modules.account.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.zipper.modules.account.domain.entity.AppLinksEntity

@Mapper
interface AppLinksMapper : BaseMapper<AppLinksEntity> {
}