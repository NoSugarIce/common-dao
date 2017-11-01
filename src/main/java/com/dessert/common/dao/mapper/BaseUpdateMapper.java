package com.dessert.common.dao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author dingjingyang@foxmail.com(dingjingyang)
 * @date 2017/8/29
 */
public interface BaseUpdateMapper<T> {

    int updateByPrimaryKey(T parameter);

    int updateByPrimaryKeyPrmMap(Map<String, Object> parameter);

    int updateByChoseKeyPrmMap(@Param("updateColum") Map<String, Object> parameter, @Param("selectColum") Map<String, Object> selectParameter);

    int insertOrUpdateByPrimaryKey(T parameter);

}
