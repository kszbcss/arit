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
package com.googlecode.arit.report;

import java.net.URL;

import com.googlecode.arit.Formatter;

public class ModuleInfo implements Formatter {
    private final Module module;
    private final String moduleUrl;
    
    public ModuleInfo(Module module, URL moduleUrl) {
        this.module = module;
        this.moduleUrl = moduleUrl == null ? null : moduleUrl.toString();
    }

    public Module getModule() {
        return module;
    }

    public String formatUrl(URL url) {
        String s = url.toString();
        return moduleUrl != null && s.startsWith(moduleUrl) ? s.substring(moduleUrl.length()) : s;
    }
}
