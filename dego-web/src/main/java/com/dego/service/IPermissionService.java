package com.dego.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dego.entity.po.Permission;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 根据role id list获取权限列表
     *
     * @param roleIdList
     * @return
     */
    List<String> getPermissionByRoles(List<Long> roleIdList);
}
