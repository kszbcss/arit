/*
 * Copyright 2010-2011 Andreas Veithen
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
package com.googlecode.arit.mbeans.mx4j;

import javax.management.MBeanServer;

import com.github.veithen.rbeans.RBeanFactory;
import com.github.veithen.rbeans.RBeanFactoryException;
import com.googlecode.arit.mbeans.MBeanAccessor;
import com.googlecode.arit.mbeans.MBeanServerInspectorPlugin;

public class MX4JMBeanServerInspectorPlugin implements MBeanServerInspectorPlugin {
    private final RBeanFactory rbf;

    public MX4JMBeanServerInspectorPlugin() {
        RBeanFactory rbf;
        try {
            rbf = new RBeanFactory(MX4JMBeanServerRBean.class, RequiredModelMBeanRBean.class);
        } catch (RBeanFactoryException ex) {
            rbf = null;
        }
        this.rbf = rbf;
    }
    
    public boolean isAvailable() {
        return rbf != null;
    }

    public MBeanAccessor inspect(MBeanServer mbs) {
        if (rbf.getRBeanInfo(MX4JMBeanServerRBean.class).getTargetClass().isInstance(mbs)) {
            return new MX4JMBeanAccessor(rbf.createRBean(MX4JMBeanServerRBean.class, mbs).getMBeanRepository(), rbf);
        } else {
            return null;
        }
    }
}
