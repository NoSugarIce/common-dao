package com.dessert.common.dao.service;

import java.util.List;

/**
 * @author frostding@gmail.com(dingjingyang)
 * @date 2017/8/31
 */
public interface BaseService<T> {

    List<T> selectList(T parameter);

    int insert(T parameter);

    int updateByPrimaryKey(T parameter);

    int deleteByPrimaryKey(T parameter);
}
