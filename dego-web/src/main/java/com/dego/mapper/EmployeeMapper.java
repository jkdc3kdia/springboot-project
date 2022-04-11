package com.dego.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dego.entity.po.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
