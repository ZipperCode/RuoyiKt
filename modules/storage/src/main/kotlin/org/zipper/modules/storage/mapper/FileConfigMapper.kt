package org.zipper.modules.storage.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import org.zipper.modules.storage.domain.entity.SysFileConfigEntity

@Mapper
interface FileConfigMapper : BaseMapper<SysFileConfigEntity> {

}