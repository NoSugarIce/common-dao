package com.dessert.common.dao.binding;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import java.io.Serializable;
import java.util.Map;

/**
 * @author frostding@gmail.com(ding)
 * @date 2017/9/30
 */
public class BeanProxy<T> implements Serializable {

    private final T object;

    private final BeanMap beanMap;

    public BeanProxy(T beanObj, Map<String, Object> propertyMap) {
        this.object = generateBean(propertyMap, beanObj);
        this.beanMap = BeanMap.create(this.object);
        initValue(propertyMap);
    }

    private void initValue(Map<String, Object> propertyMap) {
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            setValue(entry.getKey(), entry.getValue());
        }
    }

    public T getObject() {
        return object;
    }

    public Object getValue(String property) {
        return beanMap.get(property);
    }

    public void setValue(String property, Object value) {
        beanMap.put(property, value);
    }

    @SuppressWarnings("unchecked")
    private T generateBean(Map<String, Object> propertyMap, T superObj) {
        BeanGenerator beanGenerator = new BeanGenerator();
        if (superObj == null) {
            throw new IllegalArgumentException("被代理类不能为空!");
        }
        beanGenerator.setSuperclass(superObj.getClass());
        if (propertyMap != null) {
            for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
                beanGenerator.addProperty(entry.getKey(), entry.getValue().getClass());
            }
        }
        T object = (T) beanGenerator.create();
        BeanCopier copier = BeanCopier.create(superObj.getClass(), object.getClass(), false);
        copier.copy(superObj, object, null);
        return object;
    }

}
