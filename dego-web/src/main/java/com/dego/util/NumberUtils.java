package com.dego.util;

import com.dego.exception.BusinessException;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 数字处理工具
 *
 * @author huang.guo
 * @since 2019-03-14
 */
public class NumberUtils {

    /**
     * 如果number是小于等于0的，返回null
     *
     * @param number
     * @return
     */
    public static BigDecimal isNotPositiveToNull(BigDecimal number) {
        return isPositive(number) ? number : null;
    }

    /**
     * 指定数字不是正数（可以是小数）返回true
     *
     * @param number
     * @return
     */
    public static boolean isNotPositive(Number number) {
        return !isPositive(number);
    }

    /**
     * 指定数字是正数(可以是小数)返回true
     *
     * @param number
     * @return
     */
    public static boolean isPositive(Number number) {
        if (Objects.isNull(number)) {
            return false;
        }
        if (number instanceof Integer) {
            return (Integer) number > 0;
        }
        if (number instanceof Long) {
            return (Long) number > 0;
        }
        if (number instanceof Float) {
            return (Float) number > 0;
        }
        if (number instanceof Double) {
            return (Double) number > 0;
        }
        if (number instanceof Byte) {
            return (Byte) number > 0;
        }
        if (number instanceof Short) {
            return (Short) number > 0;
        }
        if (number instanceof BigDecimal) {
            return new BigDecimal(0).compareTo((BigDecimal) number) < 0;
        }

        return false;
    }

    public static Number add(Number n1, Number n2, Class type) {
        if (type == Integer.class) {
            n1 = ifNullInit(n1, Integer.TYPE);
            n2 = ifNullInit(n2, Integer.TYPE);
            return (Integer) n1 + (Integer) n2;
        }
        if (type == Long.class) {
            n1 = ifNullInit(n1, Long.TYPE);
            n2 = ifNullInit(n2, Long.TYPE);
            return (Long) n1 + (Long) n2;
        }
        if (type == Float.class) {
            n1 = ifNullInit(n1, Float.TYPE);
            n2 = ifNullInit(n2, Float.TYPE);
            return (Float) n1 + (Float) n2;
        }
        if (type == Double.class) {
            n1 = ifNullInit(n1, Double.TYPE);
            n2 = ifNullInit(n2, Double.TYPE);
            return (Double) n1 + (Double) n2;
        }
        if (type == Byte.class) {
            n1 = ifNullInit(n1, Byte.TYPE);
            n2 = ifNullInit(n2, Byte.TYPE);
            return (Byte) n1 + (Byte) n2;
        }
        if (type == Short.class) {
            n1 = ifNullInit(n1, Short.TYPE);
            n2 = ifNullInit(n2, Short.TYPE);
            return (Short) n1 + (Short) n2;
        }
        if (type == BigDecimal.class) {
            n1 = ifNullInit(n1, BigDecimal.class);
            n2 = ifNullInit(n2, BigDecimal.class);
            return ((BigDecimal) n1).add((BigDecimal) n2);
        }

        throw new BusinessException("不支持的类型！");
    }

    private static Number ifNullInit(Number number, Class type) {
        if (Objects.nonNull(number)) {
            return number;
        }

        if (type == Integer.TYPE) {
            return 0;
        }
        if (type == Long.TYPE) {
            return 0;
        }
        if (type == Float.TYPE) {
            return 0F;
        }
        if (type == Double.TYPE) {
            return 0;
        }
        if (type == Byte.TYPE) {
            return 0;
        }
        if (type == Short.TYPE) {
            return 0;
        }
        if (type == BigDecimal.class) {
            return new BigDecimal(0);
        }

        throw new BusinessException("不支持的类型！");
    }

}
