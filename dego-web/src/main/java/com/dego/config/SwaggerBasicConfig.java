package com.dego.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 *
 *
 */
@EnableSwagger2
public class SwaggerBasicConfig {
    @Bean
    public Docket createRestApi() {

        ParameterBuilder token = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        token.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false);
        ParameterBuilder deviceType = new ParameterBuilder();
        deviceType.name("deviceType").description("设备信息").modelRef(new ModelRef("string")).parameterType("header").required(false);
        ParameterBuilder siteCode = new ParameterBuilder();
        siteCode.name("siteCode").description("站点编码").modelRef(new ModelRef("string")).parameterType("header").required(false);
        ParameterBuilder isStore = new ParameterBuilder();
        isStore.name("isStore").description("是否在站点中请求").modelRef(new ModelRef("boolean")).parameterType("header").required(false);

        pars.add(token.build());
        pars.add(deviceType.build());
        pars.add(siteCode.build());
        pars.add(isStore.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(ZonedDateTime.class, String.class)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yinli"))
                .paths(PathSelectors.any())
                .build();
//                .globalOperationParameters(pars);
    }

    protected ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("demoAPI")
                .description("demo接口文档")
                .version("1.0")
                .build();
    }

}
