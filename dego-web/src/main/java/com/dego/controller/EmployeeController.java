package com.dego.controller;

import com.alibaba.fastjson.JSON;
import com.dego.constants.SystemConstant;
import com.dego.entity.vo.employee.LoginVO;
import com.dego.enums.DeviceType;
import com.dego.service.IEmployeeService;
import com.dego.threadlocal.ThreadLocalBack;
import com.dego.threadlocal.cache.EmployeeCache;
import com.dego.util.AESUtils;
import com.dego.util.AssertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 登录控制器
 *
 */
@RestController
@RequestMapping("/employee")
@Api(tags = "Back-用户管理服务")
public class EmployeeController {


    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation("登录")
    @PostMapping("login")
    public void login(@RequestBody LoginVO loginVO) {
        Subject subject = SecurityUtils.getSubject();
        loginVO.setPassword(AESUtils.encrypt(loginVO.getPassword()));
        String password = AESUtils.decrypt(loginVO.getPassword());
        AssertUtils.stringNotBlank(password, "解析密码异常");
        subject.login(new UsernamePasswordToken(loginVO.getMobile(), password, true));
        subject.getSession().setAttribute(SystemConstant.LOGIN_DEVICE_TYPE, DeviceType.PC);
        employeeService.deleteOtherSession(subject);
    }


    @ApiOperation("获取用户信息")
    @GetMapping("userInfo")
    @RequiresPermissions("dego:system:view")
    public String getUserInfo() {
        return JSON.toJSONString(ThreadLocalBack.getCurrentUser());
    }

    @ApiOperation("检测role")
    @GetMapping("testRole")
    @RequiresPermissions("dego:system:view")
    public String testRole() {
        return JSON.toJSONString(ThreadLocalBack.getCurrentUser());
    }

    @ApiOperation("检测role1")
    @GetMapping("testRole1")
    @RequiresPermissions("dego:system:view1")
    public String testRole1() {
        return JSON.toJSONString(ThreadLocalBack.getCurrentUser());
    }


    @ApiOperation("登出")
    @PostMapping("logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }

}
