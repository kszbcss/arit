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
package com.googlecode.arit.threads;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;

public class TimerThreadInspector implements ThreadInspector {
    private final Class<?> timerThreadClass;
    private final Field queueField;
    private final Field timerTaskArrayField;
    
    public TimerThreadInspector(Class<?> timerThreadClass, Field queueField, Field timerTaskArrayField) {
        this.timerThreadClass = timerThreadClass;
        this.queueField = queueField;
        this.timerTaskArrayField = timerTaskArrayField;
    }

    public ThreadDescription getDescription(Thread thread) {
        if (timerThreadClass.isInstance(thread)) {
            try {
                StringBuilder description = new StringBuilder("Timer thread");
                Set<ClassLoader> classLoaders = new HashSet<ClassLoader>();
                Object taskQueue = queueField.get(thread);
                TimerTask[] timerTasks = (TimerTask[])timerTaskArrayField.get(taskQueue);
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
                // If we have found timer tasks, then the class loaders of these tasks
                // are the most meaningful. Only add the TCCL if we have not identified
                // any tasks.
                if (classLoaders.isEmpty()) {
                    classLoaders.add(thread.getContextClassLoader());
                }
                return new ThreadDescription(description.toString(), classLoaders);
            } catch (IllegalAccessException ex) {
                throw new IllegalAccessError(ex.getMessage());
            }
        } else {
            return null;
        }
    }

    public int getPriority() {
        return 1;
    }
}