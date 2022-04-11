package com.dego.util;

import com.dego.exception.BusinessException;
import com.dego.exception.web.ResponseCode;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 继承自：org.apache.commons.collections.CollectionUtils
 *
 * @author huang.guo
 * @see org.apache.commons.collections.CollectionUtils
 * @since 2019-09-26
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

    /**
     * 把list按照groupFunction聚合并按照sumFieldFunction求和，最终返回一个列表
     * 比如：商品批次库存列表提取商品库存列表，
     * 原本是按照批次号为最小维度分组，
     * 现在按照商品为最小维度分组，并且把下面的库存数聚合起来
     *
     * @param list
     * @param groupFunction
     * @param sumField
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<T> listGroupAndSumField(List<T> list, Function<T, R> groupFunction, Field sumField) {
        Map<R, List<T>> functionMap
                = list.stream().collect(Collectors.groupingBy(groupFunction));

        return functionMap.keySet().stream().map(key ->
                sumByField(functionMap.get(key), sumField)).collect(Collectors.toList());
    }

    private static Object getValue(Field field, Object obj) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new BusinessException(ResponseCode.ERR_MSG_SYSTEM_ERROR);
        }
    }

    private static <T, N extends Number> T sumByField(List<T> list, Field field) {
        // 按照聚合过后取列表第一个元素
        T first = list.get(0);
        Number totalNum = null;
        for (T obj : list) {
            totalNum = NumberUtils.add(totalNum, (Number) getValue(field, obj), field.getType());
        }

        set(first, first.getClass(), field, totalNum);
        return first;
    }

    private static <O, C extends Class> void set(O original, C c, Field field, Object value) {
        Field[] declaredFields = c.getDeclaredFields();
        if (ArrayUtils.isNotEmpty(declaredFields)) {
            for (Field declaredField : declaredFields) {
                if (Objects.equals(field, declaredField)) {
                    declaredField.setAccessible(true);
                    try {
                        declaredField.set(original, value);
                    } catch (IllegalAccessException e) {
                        throw new BusinessException(e.getMessage());
                    }
                    break;
                }
            }
        }

        Class<?> superclass = c.getSuperclass();
        if (Objects.nonNull(superclass)) {
            set(original, superclass, field, value);
        }
    }

}
