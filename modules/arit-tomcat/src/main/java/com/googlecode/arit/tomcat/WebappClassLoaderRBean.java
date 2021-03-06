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
package com.googlecode.arit.tomcat;

import javax.naming.directory.DirContext;

import com.github.veithen.rbeans.Mapped;
import com.github.veithen.rbeans.Optional;
import com.github.veithen.rbeans.RBean;
import com.github.veithen.rbeans.SeeAlso;
import com.github.veithen.rbeans.Target;

@Target("org.apache.catalina.loader.WebappClassLoader")
@SeeAlso(ProxyDirContextRBean.class)
public interface WebappClassLoaderRBean extends RBean {
    @Optional // Doesn't exist in all versions of Tomcat 6.0
    String getContextName();
    
    @Mapped
    DirContext getResources(); 
}
