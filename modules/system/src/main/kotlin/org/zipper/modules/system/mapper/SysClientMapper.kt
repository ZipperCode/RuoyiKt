package org.zipper.modules.system.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.zipper.modules.system.domain.entity.SysClientEntity


/**
 * 授权管理Mapper接口
 *
 */
@Mapper
interface SysClientMapper : BaseMapper<SysClientEntity>
