package com.dessert.common.dao.mapper;


import com.dessert.common.dao.bean.Page;
import net.sf.cglib.beans.BeanMap;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author frostding@gmail.com(dingjingyang)
 * @date 2017/8/30
 */
public interface BaseSelectDefaultMapper<T> extends BaseSelectMapper<T> {

    int PAGER_SIZE = 10;

    /**
     * 判断是否存在
     *
     * @param parameter
     * @return true
     */
    default boolean isExist(T parameter) {
        return selectCount(parameter) > 0;
    }


    /**
     * 同一键多个值返回数据
     *
     * @param key
     * @param vals
     * @return
     */
    default List<T> selectMoreCondList(String key, Object... vals) {
        if (key == null || key.length() == 0 || vals == null || Array.getLength(vals) == 0) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        List<T> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        for (Object val : vals) {
            params.put(key, val);
            list.addAll(selectListPrmMap(params));
        }
        return list;
    }


    /**
     * 获取第一条记录
     *
     * @param parameter
     * @return
     */
    default T selectOneFist(T parameter) {
        List<T> list = selectList(parameter);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    /**
     * 获取最后一条记录
     *
     * @param parameter
     * @return
     */
    default T selectOneLast(T parameter) {
        List<T> list = selectList(parameter);
        if (list != null && !list.isEmpty()) {
            return list.get(list.size() - 1);
        }
        return null;
    }


    /**
     * 查询符合条件的个数
     *
     * @param parameter
     * @return
     */
    default int selectCount(T parameter) {
        Map<String, Object> params = beanToMap(parameter);
        return selectCountPrmMap(params);
    }


    /**
     * 分页查询
     *
     * @param page
     * @param parameter
     * @return
     */
    default Page<T> selectPage(Page<T> page, T parameter) {
        int count = selectCount(parameter);
        Map<String, Object> params = beanToMap(parameter);
        return selectPage(count, page, params);
    }


    /**
     * 分页查询
     *
     * @param page
     * @param parameter
     * @return
     */
    default Page<T> selectPage(Page<T> page, Map<String, Object> parameter) {
        int count = selectCountPrmMap(parameter);
        return selectPage(count, page, parameter);
    }


    default Page<T> selectPage(int count, Page<T> page, Map<String, Object> parameter) {
        if (count > 0) {
            processingParams(count, page, parameter);
            List<T> list = selectListPrmMap(parameter);
            page.setPageList(list);
        }
        return page;
    }


    /**
     * 分页查询
     *
     * @param page
     * @param parameter
     * @return
     */
    default Page<Map<String, Object>> selectPageMap(Page<Map<String, Object>> page, Map<String, Object> parameter) {
        int count = selectCountPrmMap(parameter);
        if (count > 0) {
            processingParams(count, page, parameter);
            List<Map<String, Object>> list = selectMapList(parameter);
            page.setPageList(list);
        }
        return page;
    }


    default void processingParams(int count, Page page, Map<String, Object> parameter) {
        int currentPage = page.getCurrentPage();
        int pageSize = page.getPageSize();
        currentPage = currentPage <= 0 ? 1 : currentPage;
        pageSize = pageSize <= 0 ? PAGER_SIZE : pageSize;

        int pageCount;
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        page.setRecordCount(count);
        page.setCurrentPage(currentPage);
        page.setPageCount(pageCount);
        page.setPageSize(pageSize);

        if (count > 0 && count > pageSize) {
            int startIndex = (currentPage - 1) * pageSize;
            parameter.put("startIndex", startIndex);
            parameter.put("pageSize", pageSize);
        }
    }


    default Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(String.valueOf(key), beanMap.get(key));
            }
        }
        return map;
    }
}
