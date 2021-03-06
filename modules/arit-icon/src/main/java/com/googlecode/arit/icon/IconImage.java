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
package com.googlecode.arit.icon;

public class IconImage {
    private final String variantName;
    private final String key;
    private final ImageData data;
    
    IconImage(String variantName, String key, ImageData data) {
        this.variantName = variantName;
        this.key = key;
        this.data = data;
    }
    
    public String getFileName() {
        return variantName + "/" + key + "." + data.getFormat().getSuffix();
    }

    public ImageData getData() {
        return data;
    }
}
