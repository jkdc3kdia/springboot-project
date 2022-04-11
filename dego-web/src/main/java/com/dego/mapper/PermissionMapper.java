package com.dego.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dego.entity.po.Permission;
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
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据role id list获取权限列表
     *
     * @param roleIdList
     * @return
     */
    List<Permission> getPermissionByRoles(List<Long> roleIdList);

}
