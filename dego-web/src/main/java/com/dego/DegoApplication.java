package com.dego;

import com.dego.swagger.SwaggerApiInitTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Description
 * @Author janus
 * @Date 2021/7/12 7:52
 * @Version 1.0
 */
@ServletComponentScan(basePackages = "com.dego.web.*")
@SpringBootApplication
@Slf4j
public class DegoApplication implements ApplicationRunner {

    @Autowired
    private SwaggerApiInitTime apiInitTime;

    public static void main(String[] args) {
        SpringApplication.run(DegoApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("DegoApplication----> run method.");
        apiInitTime.initLastDateTime();
    }
}
