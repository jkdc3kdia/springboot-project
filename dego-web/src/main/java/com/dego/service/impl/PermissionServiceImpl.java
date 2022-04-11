package com.dego.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dego.entity.po.Permission;
import com.dego.mapper.PermissionMapper;
import com.dego.service.IPermissionService;
import com.dego.util.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public List<String> getPermissionByRoles(List<Long> roleIdList) {
        List<Permission> permissionList = baseMapper.getPermissionByRoles(roleIdList);
        if (CollectionUtils.isNotEmpty(permissionList)) {
            return permissionList.stream().map(o -> o.getPermissionCode()).collect(Collectors.toList());
        }
        return null;
    }
}
