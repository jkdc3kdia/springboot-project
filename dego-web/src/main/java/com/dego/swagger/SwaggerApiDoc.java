package com.dego.swagger;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 */
@Data
public class SwaggerApiDoc {
    private String swagger = "2.0";
    private Map<String,String> info = new LinkedHashMap<>();

    private String host;

    private String basePath;
    private List<LinkedHashMap> tags = new ArrayList<>();
    private Map<String,LinkedHashMap> paths = new LinkedHashMap<>();
    private Map<String,LinkedHashMap> definitions = new LinkedHashMap<>();

    /**
     * 需要添加到权限表的url和名称
     */
    private Map<String,String> controlUrl;
}
