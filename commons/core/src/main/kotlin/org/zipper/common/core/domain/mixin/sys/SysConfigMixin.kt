package org.zipper.common.core.domain.mixin.sys
import org.zipper.common.core.domain.mixin.base.CreatorMixin

import org.zipper.common.core.domain.mixin.base.UpdaterMixin


import java.io.Serializable

interface SysConfigMixin : CreatorMixin, UpdaterMixin, Serializable {
    /**
     * 参数主键
     */
    var configId: Long?

    /**
     * 参数名称
     */
    var configName: String?

    /**
     * 参数键名
     */
    var configKey: String?

    /**
     * 参数键值
     */
    var configValue: String?

    /**
     * 系统内置（Y是 N否）
     */
    var configType: String?

    /**
     * 备注
     */
    var remark: String?
}