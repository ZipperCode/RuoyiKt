package org.zipper.framework.mybatis.core

import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.toolkit.Db
import org.zipper.common.core.ext.MapStructExt.convertTypeList
import java.io.Serializable


inline fun <reified E> BaseMapper<E>.modelClass(): Class<E> {
    return E::class.java
}

inline fun <reified E> BaseMapper<E>.selectAllList(): List<E> {
    return this.selectList(QueryWrapper<E>())
}

inline fun <reified E, reified V> BaseMapper<E>.selectVoPage(page: IPage<E>, wrapper: Wrapper<E>?): IPage<V> {
    val list = this.selectPage(page, wrapper)
    val voPage: IPage<V> = Page(page.current, page.size, page.total)
    voPage.records = list.records.convertTypeList<E, V>()
    return voPage
}

inline fun <reified E> BaseMapper<E>.insertBatch(list: Collection<E>): Boolean {
    return Db.saveBatch(list)
}

fun <E> BaseMapper<E>.updateBatchIds(idList: Collection<Serializable>): Boolean {
    return Db.updateBatchById(idList)
}