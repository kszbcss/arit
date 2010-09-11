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

import java.util.List;

import com.googlecode.arit.Provider;
import com.googlecode.arit.ProviderFinder;
import com.googlecode.arit.ResourceEnumeratorFactory;

public class JdbcDriverEnumeratorFactoryProvider implements Provider<ResourceEnumeratorFactory> {
    public ResourceEnumeratorFactory getImplementation() {
        List<DriverManagerInspector> inspectors = ProviderFinder.find(DriverManagerInspector.class);
        if (inspectors.isEmpty()) {
            return null;
        } else {
            return new JdbcDriverEnumeratorFactory(inspectors.get(0));
        }
    }
}