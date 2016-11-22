package org.ngmon.logger;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Log<T extends LogContext> {

    private final Logger logger;
    private final Class<T> contextClass;

    public Log(Class<T> contextClass, Logger logger) {
        checkClass(contextClass);
        this.contextClass = contextClass;
        this.logger = logger;
    }

    private void checkClass(Class<T> contextClass) {
        Method[] declaredMethods = contextClass.getDeclaredMethods();
        Set<String> sett = new HashSet<>();
        for (Method method : declaredMethods) {
            if (method.getAnnotation(PassThrough.class) == null) {
                if (!sett.add(method.getName())) {
                    throw new IllegalArgumentException(contextClass.getName() + " contains overloaded methods/variables");
                }
            }
        }
    }

    public T debug(String message) {
        return msg(EventLevel.DEBUG, message);
    }

    public T info(String message) {
        return msg(EventLevel.INFO, message);
    }

    public T error(String message) {
        return msg(EventLevel.ERROR, message);
    }

    private T msg(EventLevel level, String message) {
        try {
            T instance = this.contextClass.newInstance();
            instance.setLevel(level);
            instance.setMessage(message);
            instance.setLogger(this.logger);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

}
