package com.google.code.arit.websphere;

import java.lang.reflect.Field;

import com.google.code.arit.ServerContext;
import com.google.code.arit.ServerProfile;
import com.google.code.arit.ServerProfileFactory;
import com.google.code.arit.util.ReflectionUtil;

public class WASProfileFactory implements ServerProfileFactory {
    public ServerProfile createServerProfile(ServerContext serverContext) {
        Class<?> classLoaderClass = serverContext.getApplicationClassLoader().getClass();
        if (classLoaderClass.getName().equals("com.ibm.ws.classloader.CompoundClassLoader")) {
            Field nameField;
            try {
                nameField = ReflectionUtil.getField(classLoaderClass, "name");
            } catch (NoSuchFieldException ex) {
                return null;
            }
            return new WASProfile(classLoaderClass, nameField);
        } else {
            return null;
        }
    }
}