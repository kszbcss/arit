package com.googlecode.arit.shutdown.sun;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.googlecode.arit.Provider;
import com.googlecode.arit.rbeans.RBeanFactory;
import com.googlecode.arit.rbeans.RBeanFactoryException;
import com.googlecode.arit.shutdown.ShutdownHookInspector;
import com.googlecode.arit.util.ReflectionUtil;

public class SunShutdownHookInspectorProvider implements Provider<ShutdownHookInspector> {
    public ShutdownHookInspector getImplementation() {
        try {
            RBeanFactory rbf = new RBeanFactory(ShutdownRBean.class);
            final ShutdownRBean shutdown = rbf.createRBean(ShutdownRBean.class);
            Class<?> wrapperClass = Class.forName("java.lang.Shutdown$WrappedHook");
            final Field hookField = ReflectionUtil.getField(wrapperClass, "hook");
            return new ShutdownHookInspector() {
                public List<Thread> getShutdownHooks() {
                    try {
                        Collection<?> wrappedHooks = shutdown.getHooks();
                        if (wrappedHooks != null) {
                            List<Thread> hooks = new ArrayList<Thread>(wrappedHooks.size());
                            for (Object wrappedHook : wrappedHooks) {
                                hooks.add((Thread)hookField.get(wrappedHook));
                            }
                            return hooks;
                        } else {
                            return Collections.emptyList();
                        }
                    } catch (IllegalAccessException ex) {
                        throw new IllegalAccessError(ex.getMessage());
                    }
                }
            };
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (NoSuchFieldException ex) {
            return null;
        } catch (RBeanFactoryException ex) {
            return null;
        }
    }
}