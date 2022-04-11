package com.dego;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    public static void main(String[] args) {
        String md5Pwd = new Md5Hash("123456", "dego", 2).toHex();
        System.out.println(md5Pwd);


        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        Map<String, Number> map = new LinkedHashMap<String, Number>();
        map.put("jvm.thread.count", threadBean.getThreadCount());
        map.put("jvm.thread.daemon.count", threadBean.getDaemonThreadCount());
        map.put("jvm.thread.totalstarted.count", threadBean.getTotalStartedThreadCount());

        System.out.println(JSON.toJSONString(map));
        ThreadInfo[] threadInfos = threadBean.getThreadInfo(threadBean.getAllThreadIds());

        for (int i = 0; i < threadInfos.length; i++) {
            ThreadInfo threadInfo = threadInfos[i];
            System.out.println(JSON.toJSONString(threadInfo));
        }
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        System.out.println(name + "  :  " + pid);

        System.out.println(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
        System.out.println(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage());
        System.out.println(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage());


        System.out.println(ManagementFactory.getThreadMXBean().dumpAllThreads(false, false));
        //获取young GC 和full GC 次数
        List<GarbageCollectorMXBean> list1=ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean e:list1){
            System.out.println(String.format("name=%s,count=%s,time=%s",e.getName(),e.getCollectionCount(),e.getCollectionTime()));
        }
    }
}
