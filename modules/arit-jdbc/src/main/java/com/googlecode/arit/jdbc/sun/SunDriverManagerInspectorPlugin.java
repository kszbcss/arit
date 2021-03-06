/*
 * Copyright 2010-2011,2013 Andreas Veithen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.arit.jdbc.sun;

import java.util.ArrayList;
import java.util.List;

import com.github.veithen.rbeans.RBeanFactory;
import com.github.veithen.rbeans.RBeanFactoryException;
import com.googlecode.arit.jdbc.DriverManagerInspectorPlugin;

public class SunDriverManagerInspectorPlugin implements DriverManagerInspectorPlugin {
    private final DriverManagerRBean driverManager;
    
    public SunDriverManagerInspectorPlugin() {
        DriverManagerRBean driverManager;
        try {
            RBeanFactory rbf = new RBeanFactory(DriverManagerRBean.class);
            driverManager = rbf.createRBean(DriverManagerRBean.class);
        } catch (RBeanFactoryException ex) {
            driverManager = null;
        }
        this.driverManager = driverManager;
    }
    
    public boolean isAvailable() {
        return driverManager != null;
    }

    public List<Class<?>> getDriverClasses() {
        // We need to get the field value every time, because in JRE 1.6, the Vector
        // is replaced when a new driver is added.
        List<Class<?>> driverClasses = new ArrayList<Class<?>>();
        for (DriverInfoRBean driverInfo : driverManager.getDrivers()) {
            driverClasses.add(driverInfo.getDriver().getClass());
        }
        return driverClasses;
    }
}
