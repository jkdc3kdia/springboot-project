package com.dego.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dego.entity.po.Menu;
import com.dego.entity.vo.MenuListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
public interface IMenuService extends IService<Menu> {


    List<MenuListVO> getMenuList();
}
