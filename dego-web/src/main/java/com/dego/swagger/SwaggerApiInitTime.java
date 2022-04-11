package com.dego.swagger;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import io.swagger.models.Swagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述：
 *
 */
@Slf4j
@Component
public class SwaggerApiInitTime {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private DocumentationCache documentationCache;
    @Autowired
    private ServiceModelToSwagger2Mapper mapper;
    @Autowired
    private JsonSerializer jsonSerializer;
    @Autowired
    private SwaggerApiUtils swaggerApiUtils;

    public void initLastDateTime() {
        String systemName = getSystemName();
        if (systemName != null) {
            String groupName = (String) Optional.fromNullable(null).or(Docket.DEFAULT_GROUP_NAME);
            Documentation documentation = documentationCache.documentationByGroup(groupName);
            if (documentation != null) {
                Swagger swagger = mapper.mapDocumentation(documentation);
                Json json = jsonSerializer.toJson(swagger);
                String jsonString = json.value().replaceAll(SwaggerApiUtils.REF_REPLACE, SwaggerApiUtils.REF_TEMP);
                SwaggerApiDoc swaggerApiDoc = JSON.parseObject(jsonString, SwaggerApiDoc.class);
                Map<String, LinkedHashMap> paths = swaggerApiDoc.getPaths();
                Map<String, LinkedHashMap> b2cPaths = new LinkedHashMap<>();
                Map<String, LinkedHashMap> backPaths = new LinkedHashMap<>();
                for (String url : paths.keySet()) {
//                    if (url.startsWith(SystemConstant.B2C_CONTROLLER_PREFIX)) {
                    if (url.startsWith("b2c-new")) {
                        LinkedHashMap linkedHashMap = paths.get(url);
                        url = url.replace("b2c-new", "/b2c" + "_" + systemName + "/");
                        b2cPaths.put(url, linkedHashMap);
                    } else {
                        backPaths.put(url, paths.get(url));
                    }
                }
                if (b2cPaths.size() > 0) {
                    swaggerApiDoc.setPaths(b2cPaths);
                    swaggerApiUtils.updateLastDateTime(swaggerApiDoc, SwaggerApiUtils.SWAGGER_B2C);
                }
                if (backPaths.size() > 0) {
                    swaggerApiDoc.setPaths(backPaths);
                    swaggerApiUtils.updateLastDateTime(swaggerApiDoc, systemName);
                }
            }
        }
    }

    private String getSystemName() {
        for (String key : SwaggerApiUtils.SERVER_MAP.keySet()) {
            if (SwaggerApiUtils.SERVER_MAP.get(key).equalsIgnoreCase(applicationName)) {
                return key;
            }
        }
        log.info("未知系统名称");
        return null;
    }

}
