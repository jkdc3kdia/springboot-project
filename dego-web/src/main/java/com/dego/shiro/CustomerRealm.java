package com.dego.shiro;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dego.constants.SystemConstant;
import com.dego.entity.po.Employee;
import com.dego.entity.po.Role;
import com.dego.exception.BusinessException;
import com.dego.mapper.EmployeeMapper;
import com.dego.service.IEmployeeService;
import com.dego.service.IPermissionService;
import com.dego.service.IRoleService;
import com.dego.threadlocal.cache.EmployeeCache;
import com.dego.util.BeanUtils;
import com.dego.util.CollectionUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRoleService roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("shiro 授权管理...");
        EmployeeCache employee = (EmployeeCache) principalCollection.getPrimaryPrincipal();
        log.error("授权用户: " + employee.getAccount());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> roles = employee.getRoles();
        if (CollectionUtils.isNotEmpty(roles)) {
            authorizationInfo.addRoles(roles);
        }
        List<String> permissions = employee.getPermissions();
        if (CollectionUtils.isNotEmpty(permissions)) {
            authorizationInfo.addStringPermissions(permissions);
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("shiro 认证管理... ");
        // 1.从主体传过来的认证信息中，获得用户名
        String username = (String) authenticationToken.getPrincipal();
        // 2.通过用户名到数据库中获取凭证
        Employee employee = Optional.ofNullable(employeeService.getOne(Wrappers.<Employee>lambdaQuery().eq(Employee::getMobile, username)))
                .orElseThrow(() -> new UnknownAccountException("账号不存在"));
        if (employee.getIsDelete()) {
            throw new BusinessException("账号已停用");
        }
        EmployeeCache employeeCache = BeanUtils.copyJsonParse(employee, EmployeeCache.class);

        List<Role> roles = roleService.selectRoleListByAccount(employee.getAccount());
        List<String> roleList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(roles)) {
            List<Long> roleIdList = Lists.newArrayList();
            roles.stream().forEach(o -> {
                roleList.add(o.getCode());
                roleIdList.add(o.getId());
            });
            employeeCache.setRoles(roleList);

            List<String> permissionList = permissionService.getPermissionByRoles(roleIdList);
            if (CollectionUtils.isNotEmpty(permissionList)) {
                employeeCache.setPermissions(permissionList);
            }
        }

        return new SimpleAuthenticationInfo(employeeCache, employee.getPassword(), ByteSource.Util.bytes(SystemConstant.SYSTEM_NAME), this.getName());
    }
}
