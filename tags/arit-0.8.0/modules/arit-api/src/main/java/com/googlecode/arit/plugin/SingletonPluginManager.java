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
package com.googlecode.arit.plugin;

import java.util.List;

public class SingletonPluginManager<T extends Plugin> extends AbstractPluginManager<T> {
    private T plugin;

    public SingletonPluginManager(Class<T> pluginClass) {
        super(pluginClass);
    }

    protected void initialize(List<T> availablePlugins) {
        plugin = availablePlugins.isEmpty() ? null : availablePlugins.get(0);
    }
    
    public boolean isAvailable() {
        return plugin != null;
    }

    protected T getPlugin() {
        if (plugin == null) {
            throw new IllegalStateException("No available plugin");
        } else {
            return plugin;
        }
    }
}
