package com.dego.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dego.entity.po.Employee;
import org.springframework.stereotype.Service;
import org.apache.shiro.subject.Subject;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author janus
 * @since 2021-07-12
 */
@Service
public interface IEmployeeService extends IService<Employee> {

    public void deleteOtherSession(Subject currentSubject);
}
