package com.dessert.common.dao.service;

import com.dessert.common.dao.bean.Page;

import java.util.List;

/**
 * @author frostding@gmail.com(dingjingyang)
 * @date 2017/8/31
 */
public interface BaseService<T> {

    Page<T> selectPage(Page<T> page, T parameter);

    List<T> selectList(T parameter);

    int insert(T parameter);

    int updateByPrimaryKey(T parameter);

    int deleteByPrimaryKey(T parameter);
}
