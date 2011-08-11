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
package com.googlecode.arit.bundlecache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.googlecode.arit.ResourceEnumeratorFactory;
import com.googlecode.arit.ResourceType;

public class CachedResourceBundleEnumeratorFactory implements ResourceEnumeratorFactory<CachedResourceBundleEnumerator> {
    @Autowired
    @Qualifier("cached-resource-bundle")
    private ResourceType resourceType;

    @Autowired
    private ResourceBundleCacheInspector inspector;
    
    public boolean isAvailable() {
        return inspector.isAvailable();
    }

    public String getDescription() {
        return "Cached resource bundles";
    }

    public CachedResourceBundleEnumerator createEnumerator() {
        return new CachedResourceBundleEnumerator(resourceType, inspector.getCachedResourceBundles());
    }
}