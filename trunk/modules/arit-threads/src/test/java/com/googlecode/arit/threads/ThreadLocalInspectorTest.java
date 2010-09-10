package com.googlecode.arit.threads;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.googlecode.arit.ProviderFinder;
import com.googlecode.arit.threads.ThreadLocalInspector;

public class ThreadLocalInspectorTest {
    @Test
    public void test() {
        List<ThreadLocalInspector> inspectors = ProviderFinder.find(ThreadLocalInspector.class);
        Assert.assertEquals(1, inspectors.size());
        ThreadLocalInspector inspector = inspectors.get(0);
        ThreadLocal<String> threadLocal = new ThreadLocal<String>();
        threadLocal.set("test");
        Map<ThreadLocal<?>,Object> threadLocalMap = inspector.getThreadLocalMap(Thread.currentThread());
        Assert.assertEquals("test", threadLocalMap.get(threadLocal));
    }
}