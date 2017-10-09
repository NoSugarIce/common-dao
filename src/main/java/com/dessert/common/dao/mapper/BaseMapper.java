package com.dessert.common.dao.mapper;

/**
 * @author frostding@gmail.com(dingjingyang)
 * @date 2017/8/29
 */
public interface BaseMapper<T> extends
        BaseSelectDefaultMapper<T>,
        BaseInsertMapper<T>,
        BaseUpdateMapper<T>,
        BaseDeleteMapper<T> {
}
