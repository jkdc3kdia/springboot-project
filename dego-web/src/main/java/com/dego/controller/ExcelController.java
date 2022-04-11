package com.dego.controller;

import cn.hutool.poi.excel.ExcelWriter;
import com.dego.util.ExcelNewUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author janus
 * @Date 2021/7/15 11:27
 * @Version 1.0
 */
@RequestMapping("/excel")
@Controller
@Slf4j
public class ExcelController {


    /**
     * 导出Excel例子
     * @param response
     * @throws IOException
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        ExcelWriter writer = ExcelNewUtils.getExcelWriter();

        List employees = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            employees.add(new Employee("a" + i, i + 18));

        }
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.merge(1, "员工信息表");
        writer.write(employees, true);
        writer.renameSheet("第一个Sheet");
        ExcelNewUtils.writerExcel(response,writer,"Excel文件");

    }


    class Employee {

        String name;
        int age;

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
