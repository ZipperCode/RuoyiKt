package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.modules.system.domain.bo.SysDictDataBo
import org.zipper.modules.system.domain.vo.SysDictDataVo
import org.zipper.modules.system.excel.responseToExcel
import org.zipper.modules.system.service.dict.ISysDictDataService
import org.zipper.modules.system.service.dict.ISysDictTypeService

/**
 * 数据字典信息
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/dict/data")
class SysDictDataController(
    private val dictDataService: ISysDictDataService,
    private val dictTypeService: ISysDictTypeService
) {
    /**
     * 查询字典数据列表
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    fun list(dictData: SysDictDataBo, pageQuery: PageQuery): TableDataInfo<SysDictDataVo> {
        return dictDataService.selectPageDictDataList(dictData, pageQuery)
    }

    /**
     * 导出字典数据列表
     */
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    fun export(dictData: SysDictDataBo, response: HttpServletResponse) {
        val list = dictDataService.selectDictDataList(dictData)
        response.responseToExcel(list, "字典数据")
    }

    /**
     * 查询字典数据详细
     *
     * @param dictCode 字典code
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = ["/{dictCode}"])
    @ResultBody
    fun getInfo(@PathVariable dictCode: Long?): SysDictDataVo? {
        return dictDataService.selectDictDataById(dictCode)
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     */
    @GetMapping(value = ["/type/{dictType}"])
    @ResultBody
    fun dictType(@PathVariable dictType: String?): List<SysDictDataVo> {
        return dictTypeService.selectDictDataByType(dictType) ?: emptyList()
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    @ResultBody
    fun add(@Validated @RequestBody dict: SysDictDataBo) {
        dictDataService.insertDictData(dict)
    }

    /**
     * 修改保存字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun edit(@Validated @RequestBody dict: SysDictDataBo) {
        dictDataService.updateDictData(dict)
    }

    /**
     * 删除字典类型
     *
     * @param dictCodes 字典code串
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    @ResultBody
    fun remove(@PathVariable dictCodes: Array<Long>) {
        dictDataService.deleteDictDataByIds(dictCodes)
    }
}
