package com.dego.swagger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dego.util.DateUtils;
import com.dego.util.cache.CommonCacheCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述：
 *
 *
 */
@Component
public class SwaggerApiUtils {

    public static final String SWAGGER_B2C = "swagger_b2c";
    public static final Map<String, String> SERVER_MAP = new LinkedHashMap<>();

    /**
     * JSON.toJSONString(swaggerApiDoc, SerializerFeature.MapSortField);
     * swaggerApi自然排序保证替换后的$ref在最后,引用对象经测试$ref在最后一个字段才生效
     */
    public static final String REF_REPLACE = "\\$ref";
    public static final String REF_TEMP = "~ref~";

    private static final String DEFINITIONS_BEFORE = "#/definitions/";
    private static final String DEFINITIONS_AFTER = "\\$.definitions.";

    static {
        SERVER_MAP.put("oms", "oms-server-sc");
        SERVER_MAP.put("uc", "uc-server-sc");
        SERVER_MAP.put("uias", "uias-server-sc");
        SERVER_MAP.put("ts", "ts-server-sc");
        SERVER_MAP.put("tp", "tp-server-sc");
        SERVER_MAP.put("spcms", "spcms-server-sc");
        SERVER_MAP.put("bos", "bos-server-sc");
        SERVER_MAP.put("pms", "pms-server-sc");
        SERVER_MAP.put("wms", "wms-server-sc");
        SERVER_MAP.put("basic", "basic-server-sc");
        SERVER_MAP.put("tms", "tms-server-sc");
        SERVER_MAP.put("scs", "scs-server-sc");
        SERVER_MAP.put("dego", "dego-web3");
    }

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public SwaggerApiUtils(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 更新redis文档最后更新时间
     *
     * @param swaggerApiDoc
     */
    public void updateLastDateTime(SwaggerApiDoc swaggerApiDoc,String prefix) {
        String apiJson = JSON.toJSONString(swaggerApiDoc);
        // 调用JSON.parse之前需要将$ref替换
        apiJson = apiJson.replaceAll(SwaggerApiUtils.REF_REPLACE, SwaggerApiUtils.REF_TEMP);
        // 对json排序
        apiJson = JSON.toJSONString(JSON.parse(apiJson), SerializerFeature.MapSortField);
        // 替换ref引用符号
        apiJson = apiJson.replaceAll(DEFINITIONS_BEFORE, DEFINITIONS_AFTER).replaceAll(REF_TEMP,REF_REPLACE);
        // 提取url data
        JSONObject paths = JSON.parseObject(apiJson).getJSONObject("paths");
        Map<String, Map<String, JSONObject>> pathMap = JSON.parseObject(paths.toJSONString(), new TypeReference<Map<String, Map<String, JSONObject>>>() {
        });
        String format = DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS);
        for (String key : pathMap.keySet()) {
            String urlData = JSON.toJSONString(pathMap.get(key));
            String hashKey = prefix + ":" + key;
            String redisData = (String)stringRedisTemplate.opsForHash().get(CommonCacheCode.SWAGGER_API.cacheKey(), hashKey);
            String time = (String)stringRedisTemplate.opsForHash().get(CommonCacheCode.SWAGGER_API_TIME.cacheKey(), hashKey);
            // 比较urlData数据不同更新当前时间
            if (redisData == null || time == null || !urlData.equals(redisData)) {
                stringRedisTemplate.opsForHash().put(CommonCacheCode.SWAGGER_API.cacheKey(), hashKey, urlData);
                stringRedisTemplate.opsForHash().put(CommonCacheCode.SWAGGER_API_TIME.cacheKey(), hashKey, format);
            }
        }
    }

    /**
     * summary字段添加拼接接口最后更新或发布时间
     *
     * @param swaggerApiDoc
     * @param prefix
     */
    public void addLastDateTime(SwaggerApiDoc swaggerApiDoc, String prefix) {
        for (String key : swaggerApiDoc.getPaths().keySet()) {
            LinkedHashMap linkedHashMap = swaggerApiDoc.getPaths().get(key);
            for (Object o : linkedHashMap.keySet()) {
                Map data = (LinkedHashMap) linkedHashMap.get(o);
                String time = (String)stringRedisTemplate.opsForHash().get(CommonCacheCode.SWAGGER_API_TIME.cacheKey(), prefix + ":" + key);
                data.put("summary", data.get("summary") + " [" + time + "]");
            }
        }
    }

}
