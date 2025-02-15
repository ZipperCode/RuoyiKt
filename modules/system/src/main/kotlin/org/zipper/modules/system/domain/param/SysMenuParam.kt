package org.zipper.modules.system.domain.param

import com.fasterxml.jackson.annotation.JsonInclude
import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysMenuEntity
import org.zipper.modules.system.domain.mixin.SysMenuMixin

/**
 * 菜单权限业务对象 dto
 *
 */
@AutoMapper(target = SysMenuEntity::class, reverseConvertGenerate = false)
class SysMenuParam : BaseMixinVo(), SysMenuMixin {
    /**
     * 菜单ID
     */
    override var menuId: Long? = null

    /**
     * 父菜单ID
     */
    override var parentId: Long? = null

    /**
     * 菜单名称
     */
    @field:NotBlank(message = "菜单名称不能为空")
    @field:Size(
        min = 0,
        max = 50,
        message = "菜单名称长度不能超过{max}个字符"
    )
    override var menuName: String? = null

    /**
     * 显示顺序
     */
    @field:NotNull(message = "显示顺序不能为空")
    override var orderNum: Int? = null

    /**
     * 路由地址
     */
    @field:Size(min = 0, max = 200, message = "路由地址不能超过{max}个字符")
    override var path: String? = null

    /**
     * 组件路径
     */
    @field:Size(min = 0, max = 200, message = "组件路径不能超过{max}个字符")
    override var component: String? = null

    /**
     * 路由参数
     */
    override var queryParam: String? = null

    /**
     * 是否为外链（0是 1否）
     */
    override var isFrame: String? = null

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    override var isCache: String? = null

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @field:NotBlank(message = "菜单类型不能为空")
    override var menuType: String? = null

    /**
     * 显示状态（0显示 1隐藏）
     */
    override var visible: String? = null

    /**
     * 菜单状态（0正常 1停用）
     */
    override var status: String? = null

    /**
     * 权限标识
     */
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Size(min = 0, max = 100, message = "权限标识长度不能超过{max}个字符")
    override var perms: String? = null

    /**
     * 菜单图标
     */
    override var icon: String? = null

    /**
     * 备注
     */
    override var remark: String? = null
}
