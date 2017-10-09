package com.dessert.common.dao.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author frostding@gmail.com(dingjingyang)
 * @date 2017/8/29
 */
public interface BaseSelectMapper<T> {

    T selectOne(T parameter);

    Map<String, Object> selectMapOne(Map<String, Object> parameter);

    List<T> selectList(T parameter);

    List<T> selectListPrmMap(Map<String, Object> parameter);

    List<Map<String, Object>> selectMapListPrmBean(T parameter);

    List<Map<String, Object>> selectMapList(Map<String, Object> parameter);

    int selectCountPrmMap(Map<String, Object> parameter);

}
