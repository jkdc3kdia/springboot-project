package com.dego.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dego.entity.po.Menu;
import com.dego.entity.vo.MenuListVO;
import com.dego.mapper.MenuMapper;
import com.dego.service.IMenuService;
import com.dego.threadlocal.ThreadLocalBack;
import com.dego.threadlocal.cache.EmployeeCache;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<MenuListVO> getMenuList() {
        EmployeeCache currentUser = ThreadLocalBack.getCurrentUser();

        return null;
    }
}
