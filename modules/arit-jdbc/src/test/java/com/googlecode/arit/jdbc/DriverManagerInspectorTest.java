/*
 * Copyright 2010 Andreas Veithen
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
package com.googlecode.arit.jdbc;

import java.sql.DriverManager;
import java.util.List;

import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;
import org.junit.Assert;
import org.junit.Test;

public class DriverManagerInspectorTest {
    @Test
    public void test() throws Exception {
        PlexusContainer container = new DefaultPlexusContainer();
        List<DriverManagerInspector> inspectors = container.lookupList(DriverManagerInspector.class);
        DriverManagerInspector inspector = null;
        for (DriverManagerInspector candidate : inspectors) {
            if (candidate.isAvailable()) {
                inspector = candidate;
                break;
            }
        }
        Assert.assertNotNull(inspector);
        MyDriver driver = new MyDriver();
        DriverManager.registerDriver(driver);
        List<Class<?>> classes = inspector.getDriverClasses();
        Assert.assertTrue(classes.contains(MyDriver.class));
        DriverManager.deregisterDriver(driver);
        container.dispose();
    }
}
