package com.dessert.common.dao.binding;

import com.dessert.common.dao.criteria.Criteria;

import java.util.HashMap;
import java.util.Map;

/**
 * @author frostding@gmail.com(ding)
 * @date 2017/10/1
 */
public class MapperBeanFactory {

    public static <T> T newInstance(T t, Criteria criteria) {
        Map<String, Object> map = new HashMap<>();
        map.put("criteria", criteria);
        BeanProxy<T> mapperBeanProxy = new BeanProxy<>(t, map);
        return mapperBeanProxy.getObject();
    }

}
