package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity

/**
 * OSS对象存储对象
 *
 */
@TableName("sys_oss")
open class SysOssEntity : BaseMixinEntity() {
    /**
     * 对象存储主键
     */
    @field:TableId(value = "oss_id")
    var ossId: Long? = null

    /**
     * 文件名
     */
    var fileName: String? = null

    /**
     * 原名
     */
    var originalName: String? = null

    /**
     * 文件后缀名
     */
    var fileSuffix: String? = null

    /**
     * URL地址
     */
    var url: String = ""

    /**
     * 服务商
     */
    var service: String = ""
}
