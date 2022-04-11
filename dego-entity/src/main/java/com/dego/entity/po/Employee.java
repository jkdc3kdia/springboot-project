package com.dego.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("nt_employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 登录账号
     */
    @TableField("account")
    private String account;

    /**
     * 的呢两个密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 公司邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 头像
     */
    @TableField("picture")
    private String picture;

    /**
     * 性别
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否删除
     */
    @TableField("is_delete")
    private Boolean isDelete;

    /**
     * 是否已设置密码
     */
    @TableField("is_init_password")
    private Boolean isInitPassword;

    /**
     * 是否是开发账号
     */
    @TableField("is_dev")
    private Boolean isDev;


}
