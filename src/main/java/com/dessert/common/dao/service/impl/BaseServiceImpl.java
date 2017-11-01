package com.dessert.common.dao.service.impl;

import com.dessert.common.dao.bean.Page;
import com.dessert.common.dao.mapper.BaseMapper;
import com.dessert.common.dao.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dingjingyang@foxmail.com(dingjingyang)
 * @date 2017/8/31
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    public BaseMapper<T> mapper;

    @Override
    public Page<T> selectPage(Page<T> page, T parameter) {
        return mapper.selectPage(page, parameter);
    }

    @Override
    public List<T> selectList(T parameter) {
        return mapper.selectList(parameter);
    }

    @Override
    public int insert(T parameter) {
        return mapper.insert(parameter);
    }

    @Override
    public int updateByPrimaryKey(T parameter) {
        return mapper.updateByPrimaryKey(parameter);
    }

    @Override
    public int deleteByPrimaryKey(T parameter) {
        return mapper.deleteByPrimaryKey(parameter);
    }
}
