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
package com.googlecode.arit.jmx;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.googlecode.arit.ResourceEnumerator;
import com.googlecode.arit.ResourceEnumeratorFactory;
import com.googlecode.arit.report.ClassLoaderIdProvider;

@ManagedResource(objectName="com.googlecode.arit:type=Cleaner", description="Cleans resources that cause memory leaks")
public class Cleaner {
    @Autowired
    private Set<ResourceEnumeratorFactory<?>> resourceEnumeratorFactories;
    
    @Autowired
    private ClassLoaderIdProvider classLoaderIdProvider;
    
    @ManagedOperation(description="Clean resources linked to a given class loader")
    @ManagedOperationParameters(
        @ManagedOperationParameter(name="classLoaderId", description="The ID of the class loader")
    )
    public void clean(Integer classLoaderId) {
        for (ResourceEnumeratorFactory<?> resourceEnumeratorFactory : resourceEnumeratorFactories) {
            if (resourceEnumeratorFactory.isAvailable()) {
                ResourceEnumerator resourceEnumerator = resourceEnumeratorFactory.createEnumerator();
                while (resourceEnumerator.nextResource()) {
                    while (resourceEnumerator.nextClassLoaderReference()) {
                        Integer id = classLoaderIdProvider.getClassLoaderId(resourceEnumerator.getReferencedClassLoader(), false);
                        if (id != null && id.equals(classLoaderId)) {
                            resourceEnumerator.cleanup();
                        }
                    }
                }
            }
        }
    }
}
