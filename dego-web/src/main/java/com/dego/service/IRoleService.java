package com.dego.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dego.entity.po.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
public interface IRoleService extends IService<Role> {

    /**
     * 根据账号名查找role list
     * @param account
     * @return
     */
    List<Role> selectRoleListByAccount(String account);
}
