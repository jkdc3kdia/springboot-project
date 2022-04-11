package com.dego.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dego.entity.po.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 根据账号名查找role list
     *
     * @param account
     * @return
     */
    List<Role> selectRoleListByAccount(String account);
}
