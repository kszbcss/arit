package com.googlecode.arit.jdbc.ibm;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.arit.Provider;
import com.googlecode.arit.jdbc.DriverManagerInspector;
import com.googlecode.arit.util.ReflectionUtil;

public class IBMDriverManagerInspectorProvider implements Provider<DriverManagerInspector> {
    public DriverManagerInspector getImplementation() {
        try {
            // "theDrivers" is a final field; we only need to retrieve it once
            Field theDriversField = ReflectionUtil.getField(DriverManager.class, "theDrivers");
            final List<?> drivers = (List<?>)theDriversField.get(null);
            return new DriverManagerInspector() {
                public List<Class<?>> getDriverClasses() {
                    List<Class<?>> driverClasses = new ArrayList<Class<?>>(drivers.size());
                    for (Object driver : drivers) {
                        driverClasses.add(driver.getClass());
                    }
                    return driverClasses;
                }
            };
        } catch (NoSuchFieldException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            throw new IllegalAccessError(ex.getMessage());
        }
    }
}