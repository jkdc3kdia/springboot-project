package com.dego.controller;

import com.dego.entity.vo.MenuListVO;
import com.dego.service.IMenuService;
import com.dego.web.ApiRespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Description
 * @Author janus
 * @Date 2022/1/20 9:51
 * @Version 1.0
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单服务")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("获取系统菜单")
    @GetMapping(value = "/list/{sysCode}")
    public ApiRespResult<List<MenuListVO>> list() {
        List<MenuListVO> list = menuService.getMenuList();
        return new ApiRespResult<>(list);
    }
}
