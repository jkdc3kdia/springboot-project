package com.dego.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dego.exception.BusinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 描述：继承至org.springframework.beans.BeanUtils
 *
 *
 */
@Slf4j
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static <T> T copy(Object source, Class<T> cls) {
        try {
            T target = cls.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 转换为json再解析为对象
     * 相对BeanUtils.copyProperties有的类型不能复制和引用类型不能转换等
     * Boolean->Integer/Integer->String/LocalDateTime->Long等
     *
     * @param source
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T copyJsonParse(Object source, Class<T> cls) {
        return JSON.parseObject(JSON.toJSONString(source), cls);
    }

    public static <T> T copyJsonParse(Object source, TypeReference<T> typeReference) {
        return JSON.parseObject(JSON.toJSONString(source), typeReference);
    }

    public static <T> List<T> copyJsonParseList(Object source, Class<T> cls) {
        return JSON.parseObject(JSON.toJSONString(source), new TypeReference<List<T>>(cls) {
        });
    }

    public static void main(String[] args) {
        TestPO testPO = new TestPO();
        testPO.setB(1);
        testPO.setLocalDate(LocalDate.now());
        testPO.setLocalDateTime(LocalDateTime.now());
        testPO.setLocalDateTimeToDate(LocalDateTime.now());
        testPO.setDate(new Date());
        testPO.setStr("123");
        TestVO testVO = BeanUtils.copyJsonParse(testPO, TestVO.class);
        System.out.println(JSON.toJSONString(testVO, true));
        TestPO testPO2 = BeanUtils.copyJsonParse(testVO, TestPO.class);
        System.out.println(JSON.toJSONString(testPO2, true));

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            BeanUtils.copyJsonParse(testVO, TestPO.class);
        }
        System.out.println("copyJsonParse: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            BeanUtils.copy(testVO, TestPO.class);
        }
        System.out.println("copyProperties: " + (System.currentTimeMillis() - start));
    }

    /**
     * list深拷贝
     *
     * @param originalList
     * @param <T>
     * @return deepCopyList
     */
    public static <T> List<T> copyDeepList(List<T> originalList) {
        return JSON.parseObject(JSON.toJSONString(originalList),
                new TypeReference<List<T>>(originalList.get(0).getClass()) {
                });
    }

    @Data
    private static class TestPO {
        private LocalDate localDate;
        private LocalDateTime localDateTime;
        private LocalDateTime localDateTimeToDate;
        private Date date;
        private String str;
        private Integer b;
    }

    @Data
    private static class TestVO {
        private Long localDate;
        private Long localDateTime;
        private Date localDateTimeToDate;
        private Long date;
        private Integer str;
        private Boolean b;
    }

}
