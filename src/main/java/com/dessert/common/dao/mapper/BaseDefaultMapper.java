package com.dessert.common.dao.mapper;


import com.dessert.common.dao.bean.Page;
import net.sf.cglib.beans.BeanMap;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author dingjingyang@foxmail.com(dingjingyang)
 * @date 2017/8/30
 */
public interface BaseDefaultMapper<T> extends BaseSelectMapper<T>, BaseInsertMapper<T>, BaseUpdateMapper<T> {

    int PAGER_SIZE = 10;


    /**
     * 根据选择的字段更新
     *
     * @param parameter 参数
     * @param choseKey  指定的keys
     * @return 更新的数量
     */
    default int updateByChoseKey(T parameter, String... choseKey) {
        if (parameter == null || choseKey == null || Array.getLength(choseKey) == 0) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        Map<String, Object> beanMap = beanToMap(parameter);
        Map<String, Object> selectParameter = new HashMap<>();
        for (String key : choseKey) {
            if (beanMap.containsKey(key)) {
                selectParameter.put(key, beanMap.get(key));
                beanMap.put(key, null);
            }
        }
        return updateByChoseKeyPrmMap(beanMap, selectParameter);
    }


    /**
     * 根据选择的字段判断插入或更新
     *
     * @param parameter 参数
     * @param choseKey  指定的keys
     * @return 插入或更新的数量
     */
    default int insertOrUpdateByChoseKey(T parameter, String... choseKey) {
        if (parameter == null || choseKey == null || Array.getLength(choseKey) == 0) {
            throw new IllegalArgumentException("参数不能为空!");
        }
        Map<String, Object> beanMap = beanToMap(parameter);
        Map<String, Object> selectParameter = new HashMap<>();
        for (String key : choseKey) {
            if (beanMap.containsKey(key)) {
                selectParameter.put(key, beanMap.get(key));
                beanMap.put(key, null);
            }
        }
        int count = selectCountPrmMap(selectParameter);
        if (count > 1) {
            throw new RuntimeException("请检查参数信息,该参数匹配到多条数据!");
        } else if (count == 1) {
            return updateByChoseKeyPrmMap(beanMap, selectParameter);
        } else {
            return insert(parameter);
        }
    }


    /**
     * 判断是否存在
     *
     * @param parameter 参数
     * @return true:存在,false:不存在
     */
    default boolean isExist(T parameter) {
        return selectCount(parameter) > 0;
    }

    /**
     * 根据主键判断是否存在
     *
     * @param vals 主键
     * @return true:存在,false:不存在
     */
    default boolean isExistByPrimaryKey(Serializable... vals) {
        return Objects.nonNull(selectByPrimaryKey(vals));
    }

    /**
     * 查询符合条件的个数
     *
     * @param parameter 参数
     * @return 数量
     */
    default int selectCount(T parameter) {
        Map<String, Object> params = beanToMap(parameter);
        return selectCountPrmMap(params);
    }


    /**
     * 同一键多个值返回数据
     *
     * @param key  选择的字段
     * @param vals 值
     * @return 符合条件的集合
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
     * @param parameter 参数
     * @return 一条记录
     */
    default T selectFist(T parameter) {
        List<T> list = selectListByQuantity(parameter, 1);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取指定数量条记录
     *
     * @param parameter 参数
     * @param quantity  指定数量
     * @return 指定数量条记录
     */
    default List<T> selectListByQuantity(T parameter, int quantity) {
        Page<T> page = new Page<>();
        page.setPageSize(quantity);
        page = selectPage(page, parameter);
        return page.getPageList();
    }


    /**
     * 查询Map 以mapKey为键,一条返回数据为值
     *
     * @param parameter 参数
     * @param mapKey    记录的一个字段
     * @return 以mapKey为键, 一条返回数据为值
     */
    @SuppressWarnings("unchecked")
    default <K> Map<K, T> selectConversionMap(T parameter, String mapKey) {
        final List<T> list = selectList(parameter);
        if (list == null || list.isEmpty()) {
            return null;
        }
        Map<K, T> map = new HashMap<>();
        for (T t : list) {
            if (t != null) {
                BeanMap beanMap = BeanMap.create(t);
                if (beanMap.containsKey(mapKey)) {
                    map.put((K) beanMap.get(mapKey), t);
                }
            }
        }
        return map;
    }


    /**
     * 分页查询
     *
     * @param page      分页参数
     * @param parameter 参数
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
     * @param page      分页参数
     * @param parameter 参数
     * @return
     */
    default Page<T> selectPage(Page<T> page, Map<String, Object> parameter) {
        int count = selectCountPrmMap(parameter);
        return selectPage(count, page, parameter);
    }


    /**
     * 分页查询
     *
     * @param count     总记录数
     * @param page      分页参数
     * @param parameter 分页参数
     * @return
     */
    default Page<T> selectPage(int count, Page<T> page, Map<String, Object> parameter) {
        if (count > 0 && (page.getCurrentPage() - 1) * page.getPageSize() < count) {
            processingParams(count, page, parameter);
            List<T> list = selectListPrmMap(parameter);
            page.setPageList(list);
        }
        return page;
    }


    /**
     * 分页查询
     *
     * @param page      分页参数
     * @param parameter 分页参数
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
