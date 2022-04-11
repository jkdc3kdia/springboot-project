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
 * 用户与角色关系表
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("nt_employee_role_rel")
public class EmployeeRoleRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户账号
     */
    @TableField("account")
    private String account;

    /**
     * 角色名称
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


}
