package com.dessert.common.dao.mapper;

import java.util.Map;

/**
 * @author frostding@gmail.com(dingjingyang)
 * @date 2017/8/29
 */
public interface BaseUpdateMapper<T> {

    int updateByPrimaryKey(T parameter);

    int updateByPrimaryKeyPrmMap(Map<String, Object> parameter);

    int update(T parameter);

    int updatePrmMap(Map<String, Object> parameter);
}
