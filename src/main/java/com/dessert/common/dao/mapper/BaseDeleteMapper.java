package com.dessert.common.dao.mapper;

import java.util.Map;

/**
 * @author dingjingyang@foxmail.com(dingjingyang)
 * @date 2017/8/29
 */
public interface BaseDeleteMapper<T> {

    int deleteByPrimaryKey(T parameter);

    int deleteByPrimaryKeyPrmMap(Map<String, Object> parameter);

    int delete(T parameter);

    int deletePrmMap(Map<String, Object> parameter);
}
