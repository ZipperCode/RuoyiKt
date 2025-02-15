package org.zipper.modules.system.domain.vo

import cn.hutool.core.lang.tree.Tree

/**
 * 角色菜单列表树信息
 *
 * @author Michelle.Chung
 */
class MenuTreeSelectVo {
    /**
     * 选中菜单列表
     */
    var  checkedKeys: List<Long>? = null

    /**
     * 菜单下拉树结构列表
     */
    var  menus: List<Tree<Long>>? = null
}
