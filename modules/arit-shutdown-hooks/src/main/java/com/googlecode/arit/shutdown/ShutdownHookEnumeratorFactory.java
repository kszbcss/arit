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
package com.googlecode.arit.shutdown;

import java.util.List;

import org.codehaus.plexus.component.annotations.Component;

import com.googlecode.arit.ProviderFinder;
import com.googlecode.arit.ResourceEnumerator;
import com.googlecode.arit.ResourceEnumeratorFactory;

@Component(role=ResourceEnumeratorFactory.class, hint="shutdown")
public class ShutdownHookEnumeratorFactory implements ResourceEnumeratorFactory {
    private final ShutdownHookInspector inspector;

    public ShutdownHookEnumeratorFactory() {
        List<ShutdownHookInspector> inspectors = ProviderFinder.find(ShutdownHookInspector.class);
        inspector = inspectors.isEmpty() ? null : inspectors.get(0);
    }

    public boolean isAvailable() {
        return inspector != null;
    }

    public String getDescription() {
        return "Shutdown hooks";
    }

    public ResourceEnumerator createEnumerator() {
        return new ShutdownHookEnumerator(inspector.getShutdownHooks());
    }
}
