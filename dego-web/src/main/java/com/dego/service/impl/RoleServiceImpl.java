package com.dego.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dego.entity.po.Role;
import com.dego.mapper.RoleMapper;
import com.dego.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Override
    public List<Role> selectRoleListByAccount(String account) {
        return baseMapper.selectRoleListByAccount(account);
    }
}
