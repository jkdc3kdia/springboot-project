package com.dego.entity.vo.employee;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description
 * @Author janus
 * @Date 2021/7/27 14:31
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class LoginVO {


    private String mobile;

    private String password;


}
