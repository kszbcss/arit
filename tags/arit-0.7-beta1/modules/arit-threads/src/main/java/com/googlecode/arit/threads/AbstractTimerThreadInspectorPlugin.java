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
package com.googlecode.arit.threads;

import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.googlecode.arit.ResourceType;

public abstract class AbstractTimerThreadInspectorPlugin implements ThreadInspectorPlugin {
    @Autowired
    @Qualifier("timerthread")
    private ResourceType resourceType;
    
    protected abstract TimerTask[] getTimerTasks(Thread thread);
    
    public ThreadDescription getDescription(Thread thread) {
        TimerTask[] timerTasks = getTimerTasks(thread);
        if (timerTasks != null) {
            StringBuilder description = new StringBuilder("Timer thread");
            Set<ClassLoader> classLoaders = new HashSet<ClassLoader>();
            boolean first = true;
            for (TimerTask task : timerTasks) {
                if (task != null) {
                    Class<?> taskClass = task.getClass();
                    if (first) {
                        description.append("; tasks: ");
                        first = false;
                    } else {
                        description.append(", ");
                    }
                    description.append(taskClass.getName());
                    classLoaders.add(taskClass.getClassLoader());
                }
            }
            return new ThreadDescription(resourceType, description.toString(), classLoaders);
        } else {
            return null;
        }
    }

    public int getPriority() {
        return 1;
    }
}