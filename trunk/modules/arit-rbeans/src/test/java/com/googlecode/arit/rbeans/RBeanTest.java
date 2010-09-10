package com.googlecode.arit.rbeans;

import junit.framework.Assert;

import org.junit.Test;

public class RBeanTest {
    @Test
    public void testPrivateAttributeAccess() {
        RBeanFactory rbf = new RBeanFactory();
        DummyClass1RBean rbean = rbf.createRBean(DummyClass1RBean.class, new DummyClass1());
        Assert.assertEquals("somevalue", rbean.getValue());
        Assert.assertEquals("Hello (my value is somevalue)", rbean.sayHello());
    }
    
    @Test
    public void testReturnValueWrapping() {
        RBeanFactory rbf = new RBeanFactory();
        ParentRBean rbean = rbf.createRBean(ParentRBean.class, new Parent());
        Assert.assertEquals("Hello", rbean.getChild().sayHello());
    }
}