package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.constant.UserConstants
import org.zipper.common.core.exception.ServiceException
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.web.ext.validateRow
import org.zipper.modules.system.domain.bo.SysPostBo
import org.zipper.modules.system.domain.vo.SysPostVo
import org.zipper.modules.system.excel.responseToExcel
import org.zipper.modules.system.service.post.ISysPostService

/**
 * 岗位信息操作处理
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/post")
class SysPostController(private val postService: ISysPostService) {
    /**
     * 获取岗位列表
     */
    @SaCheckPermission("system:post:list")
    @GetMapping("/list")
    fun list(post: SysPostBo, pageQuery: PageQuery): TableDataInfo<SysPostVo> {
        return postService.selectPagePostList(post, pageQuery)
    }

    /**
     * 导出岗位列表
     */
    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:post:export")
    @PostMapping("/export")
    fun export(post: SysPostBo, response: HttpServletResponse) {
        val list = postService.selectPostList(post)
        response.responseToExcel(list, "岗位数据")
    }

    /**
     * 根据岗位编号获取详细信息
     *
     * @param postId 岗位ID
     */
    @SaCheckPermission("system:post:query")
    @GetMapping(value = ["/{postId}"])
    @ResultBody
    fun getInfo(@PathVariable postId: Long?): SysPostVo? {
        return postService.selectPostById(postId)
    }

    /**
     * 新增岗位
     */
    @SaCheckPermission("system:post:add")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ResultBody
    fun add(@Validated @RequestBody post: SysPostBo) {
        if (!postService.checkPostNameUnique(post)) {
            throw ServiceException("新增岗位'" + post.postName + "'失败，岗位名称已存在")
        } else if (!postService.checkPostCodeUnique(post)) {
            throw ServiceException("新增岗位'" + post.postName + "'失败，岗位编码已存在")
        }
        postService.insertPost(post).validateRow()
    }

    /**
     * 修改岗位
     */
    @SaCheckPermission("system:post:edit")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun edit(@Validated @RequestBody post: SysPostBo) {
        if (!postService.checkPostNameUnique(post)) {
            throw ServiceException("修改岗位'" + post.postName + "'失败，岗位名称已存在")
        } else if (!postService.checkPostCodeUnique(post)) {
            throw ServiceException("修改岗位'" + post.postName + "'失败，岗位编码已存在")
        } else if (UserConstants.POST_DISABLE == post.status && postService.countUserPostById(post.postId) > 0) {
            throw ServiceException("该岗位下存在已分配用户，不能禁用!")
        }
        postService.updatePost(post).validateRow()
    }

    /**
     * 删除岗位
     *
     * @param postIds 岗位ID串
     */
    @SaCheckPermission("system:post:remove")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    @ResultBody
    fun remove(@PathVariable postIds: Array<Long>) {
        postService.deletePostByIds(postIds).validateRow()
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    @ResultBody
    fun optionselect(): List<SysPostVo> {
        val postBo = SysPostBo()
        postBo.status = UserConstants.POST_NORMAL
        val posts = postService.selectPostList(postBo)
        return posts
    }
}
