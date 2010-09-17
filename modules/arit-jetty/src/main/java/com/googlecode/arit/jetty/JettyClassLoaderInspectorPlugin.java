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
package com.googlecode.arit.jetty;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

import com.googlecode.arit.ModuleInspector;
import com.googlecode.arit.ModuleInspectorPlugin;
import com.googlecode.arit.ModuleType;
import com.googlecode.arit.rbeans.RBeanFactory;
import com.googlecode.arit.rbeans.RBeanFactoryException;

@Component(role=ModuleInspectorPlugin.class, hint="jetty")
public class JettyClassLoaderInspectorPlugin implements ModuleInspectorPlugin {
    private final RBeanFactory rbf;
    
    @Requirement(hint="war")
    private ModuleType warModuleType;
    
    public JettyClassLoaderInspectorPlugin() {
        RBeanFactory rbf;
        try {
            rbf = new RBeanFactory(WebAppClassLoaderRBean.class);
        } catch (RBeanFactoryException ex) {
            rbf = null;
        }
        this.rbf = rbf;
    }
    
    public boolean isAvailable() {
        return rbf != null;
    }

    public ModuleInspector createModuleInspector() {
        return new JettyModuleInspector(rbf, warModuleType);
    }
}
