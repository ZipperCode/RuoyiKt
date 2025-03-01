package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.common.core.domain.mixin.base.LogicDeleteMixin
import org.zipper.common.core.domain.mixin.sys.SysClientMixin

/**
 * 授权管理对象 sys_client
 *
 */
@TableName("sys_client")
class SysClientEntity : BaseMixinEntity(), SysClientMixin, LogicDeleteMixin {
    /**
     * id
     */
    @field:TableId(value = "id", type = IdType.AUTO)
    override var id: Long? = null

    /**
     * 客户端id
     */
    override var clientId: String? = null

    /**
     * 客户端key
     */
    override var clientKey: String? = null

    /**
     * 客户端秘钥
     */
    override var clientSecret: String? = null

    /**
     * 授权类型
     */
    override var grantType: String? = null

    /**
     * 设备类型
     */
    override var deviceType: String? = null

    /**
     * token活跃超时时间
     */
    override var activeTimeout: Long? = null

    /**
     * token固定超时时间
     */
    override var timeout: Long? = null

    /**
     * 状态（0正常 1停用）
     */
    override var status: String? = null

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @field:TableLogic
    override var deleted: String? = LogicDeleteMixin.NORMAL
}
