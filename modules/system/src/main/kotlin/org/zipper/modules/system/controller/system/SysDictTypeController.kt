package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.exception.ServiceException
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.modules.system.domain.bo.SysDictTypeBo
import org.zipper.modules.system.domain.vo.SysDictTypeVo
import org.zipper.modules.system.excel.responseToExcel
import org.zipper.modules.system.service.dict.ISysDictTypeService

/**
 * 数据字典信息
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/dict/type")
class SysDictTypeController(private val dictTypeService: ISysDictTypeService) {
    /**
     * 查询字典类型列表
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    fun list(dictType: SysDictTypeBo, pageQuery: PageQuery): TableDataInfo<SysDictTypeVo> {
        return dictTypeService.selectPageDictTypeList(dictType, pageQuery)
    }

    /**
     * 导出字典类型列表
     */
    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    fun export(dictType: SysDictTypeBo, response: HttpServletResponse) {
        val list = dictTypeService.selectDictTypeList(dictType)
        response.responseToExcel<SysDictTypeVo>(list, "字典类型")
    }

    /**
     * 查询字典类型详细
     *
     * @param dictId 字典ID
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = ["/{dictId}"])
    @ResultBody
    fun getInfo(@PathVariable dictId: Long?): SysDictTypeVo? {
        return dictTypeService.selectDictTypeById(dictId)
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    @ResultBody
    fun add(@Validated @RequestBody dict: SysDictTypeBo) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            throw ServiceException("新增字典'" + dict.dictName + "'失败，字典类型已存在")
        }
        dictTypeService.insertDictType(dict)
    }

    /**
     * 修改字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun edit(@Validated @RequestBody dict: SysDictTypeBo) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            throw ServiceException("修改字典'" + dict.dictName + "'失败，字典类型已存在")
        }
        dictTypeService.updateDictType(dict)
    }

    /**
     * 删除字典类型
     *
     * @param dictIds 字典ID串
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    @ResultBody
    fun remove(@PathVariable dictIds: Array<Long>) {
        dictTypeService.deleteDictTypeByIds(dictIds)
    }

    /**
     * 刷新字典缓存
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    @ResultBody
    fun refreshCache() {
        dictTypeService.resetDictCache()
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    @ResultBody
    fun optionselect(): List<SysDictTypeVo> {
        return dictTypeService.selectDictTypeAll()
    }
}
