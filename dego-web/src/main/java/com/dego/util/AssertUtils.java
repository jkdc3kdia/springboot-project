package com.dego.util;


import com.dego.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;


public class AssertUtils {

    /**
     * 邮箱
     */
    public static final Pattern EMAIL = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

    /**
     * 手机号
     */
    public static final Pattern MOBILE = Pattern.compile(
            "^(13\\d|14\\d|15\\d|16\\d|17\\d|18\\d|19\\d)\\d{8}$");

    /**
     * 身份证
     */
    public static final Pattern IDENTITY_CARD = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))(0[1-9]|([1|2][0-9])|3[0-1])((\\d{4})|\\d{3}X)$");

    /**
     * 字符串不能为空
     *
     * @param message  断言字符串
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息
     */
    public static String stringNotBlank(String message, RuntimeException e, String errorMsg) {
        if (StringUtils.isBlank(message)) {
            exceptionHandler(e, errorMsg);
        }
        return message;
    }

    /**
     * 字符串不能为空
     *
     * @param message  断言字符串
     * @param errorMsg 异常信息
     * @return
     */
    public static String stringNotBlank(String message, String errorMsg) {
        if (StringUtils.isBlank(message)) {
            exceptionHandler(null, errorMsg);
        }
        return message;
    }

    /**
     * 字符串 message1 message2 是否相等
     *
     * @param message1 字符串
     * @param message2 字符串
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息(message1)
     */
    public static String stringNotEqual(String message1, String message2, RuntimeException e, String errorMsg) {
        if (StringUtils.equals(message1, message2)) {
            exceptionHandler(e, errorMsg);
        }
        return message1;
    }

    /**
     * 字符串 message1 message2 是否相等 (忽略大小写)
     *
     * @param message1 字符串
     * @param message2 字符串
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息(message1)
     */
    public static String stringNotEqualIgnoreCase(String message1, String message2, RuntimeException e,
                                                  String errorMsg) {
        if (StringUtils.equalsIgnoreCase(message1, message2)) {
            exceptionHandler(e, errorMsg);
        }
        return message1;
    }

    /**
     * 正则匹配
     *
     * @param message  断言字符串
     * @param regexp   正则表达式对象
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息
     */
    public static String stringNotMatch(String message, Pattern regexp, RuntimeException e, String errorMsg) {
        objectNotEmpty(regexp, null, "regexp can't be null!");
        if (!regexp.matcher(message).matches()) {
            exceptionHandler(e, errorMsg);
        }
        return message;
    }

    /**
     * 正则匹配
     *
     * @param message  断言字符串
     * @param regexp   正则表达式(字符串)
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源信息
     */
    public static String stringNotMatch(String message, String regexp, RuntimeException e, String errorMsg) {
        Pattern pattern = Pattern.compile(regexp);
        if (!pattern.matcher(message).matches()) {
            exceptionHandler(e, errorMsg);
        }
        return message;
    }

    /**
     * 对象不能为空
     *
     * @param obj      断言对象
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源对象
     */
    public static Object objectNotEmpty(Object obj, RuntimeException e, String errorMsg) {
        if (obj == null) {
            exceptionHandler(e, errorMsg);
        }
        return obj;
    }

    /**
     * 对象不能为空
     *
     * @param obj      断言对象
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源对象
     */
    public static Object objectNotEmpty(Object obj, String errorMsg) {
        if (obj == null) {
            exceptionHandler(null, errorMsg);
        }
        return obj;
    }

    /**
     * 对象不相等
     *
     * @param obj1     对象1
     * @param obj2     对象2
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源对象obj1
     */
    public static Object objectNotEqual(Object obj1, Object obj2, RuntimeException e, String errorMsg) {
        if (obj1 != obj2) {
            exceptionHandler(e, errorMsg);
        }
        return obj1;
    }


    /**
     * 集合不能为空
     *
     * @param cols     断言集合
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源集合
     */
    public static Collection<?> collectionNotEmpty(Collection<?> cols, RuntimeException e, String errorMsg) {
        if (cols == null || cols.isEmpty()) {
            exceptionHandler(e, errorMsg);
        }
        return cols;
    }

    /**
     * 集合不能为空
     *
     * @param cols     断言集合
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源集合
     */
    public static Collection<?> collectionNotEmpty(Collection<?> cols, String errorMsg) {
        if (cols == null || cols.isEmpty()) {
            exceptionHandler(null, errorMsg);
        }
        return cols;
    }

    /**
     * 集合不能为空
     *
     * @param cols     断言集合
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源集合
     */
    public static Map<?,?> mapNotEmpty(Map<?,?> cols, String errorMsg) {
        if (cols == null || cols.isEmpty()) {
            exceptionHandler(null, errorMsg);
        }
        return cols;
    }


    /**
     * 数组不能为空
     *
     * @param array
     * @param e
     * @param errorMsg
     * @return
     */
    public static Object[] arrayNotEmpty(Object[] array, RuntimeException e, String errorMsg) {
        if (array == null || array.length == 0) {
            exceptionHandler(e, errorMsg);
        }
        return array;
    }

    /**
     * map 对象不能为空
     *
     * @param map      断言MAP
     * @param e        自定义异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源MAP
     */
    public static Map<?, ?> mapNotEmpty(Map<?, ?> map, RuntimeException e, String errorMsg) {
        if (map == null || map.isEmpty()) {
            exceptionHandler(e, errorMsg);
        }
        return map;
    }

    /**
     * 异常处理
     *
     * @param e
     * @param errorMsg
     */
    private static void exceptionHandler(RuntimeException e, String errorMsg) {
        if (e != null) {
            throw e;
        } else {
            throw new IllegalArgumentException(errorMsg);
        }
    }

    /**
     * 抛出异常
     * @param errorMsg 异常信息(如果自定义异常 e为空的话, 使用errorMsg)
     * @return 源对象
     */
    public static void throwExceptionMsg( String errorMsg) {
            exceptionHandler(null, errorMsg);
    }

    /**
     * 数据校验
     */
    public static void isTrue(boolean b,String message) {
        if(b){
            throw new BusinessException(message);
        }
    }

    public static void isFalse(boolean b,String message) {
        if(!b){
            throw new BusinessException(message);
        }
    }
}
