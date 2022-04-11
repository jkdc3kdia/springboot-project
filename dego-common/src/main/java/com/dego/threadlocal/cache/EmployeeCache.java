package com.dego.threadlocal.cache;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author autogeneration
 * @since 2020-12-21
 */
@Data
public class EmployeeCache implements Serializable {


    private Long id;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 用户姓名
     */
    private String realName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 头像
     */
    private String picture;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 权限
     */
    private List<String> permissions;


}
