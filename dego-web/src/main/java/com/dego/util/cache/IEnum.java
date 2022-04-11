package com.dego.util.cache;


/**
 * 定义了ENUM_TYPE("code", "desc")形式的枚举
 *
 * @author huang.guo
 * @since 2019-09-18
 */
public interface IEnum<C, D> {
    C getCode();

    D getDesc();
}
