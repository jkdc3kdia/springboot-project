package com.dego.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dego.entity.po.EmployeeRoleRel;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户与角色关系表 Mapper 接口
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Mapper
public interface EmployeeRoleRelMapper extends BaseMapper<EmployeeRoleRel> {

}
