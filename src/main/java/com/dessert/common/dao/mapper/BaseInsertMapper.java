package com.dessert.common.dao.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author dingjingyang@foxmail.com(dingjingyang)
 * @date 2017/8/29
 */
public interface BaseInsertMapper<T> {

    int insert(T parameter);

    int insertPrmMap(Map<String, Object> parameter);

    int insertBatch(List<T> list);
}
