package com.dego.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dego.entity.po.EmployeeRoleRel;
import com.dego.mapper.EmployeeRoleRelMapper;
import com.dego.service.IEmployeeRoleRelService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户与角色关系表 服务实现类
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Service
public class EmployeeRoleRelServiceImpl extends ServiceImpl<EmployeeRoleRelMapper, EmployeeRoleRel> implements IEmployeeRoleRelService {

}
