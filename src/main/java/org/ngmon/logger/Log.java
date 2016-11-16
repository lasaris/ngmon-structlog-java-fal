package org.ngmon.logger;

public class Log<T extends LogContext> {

    private final Logger logger;
    private final Class<T> contextClass;

    public Log(Class<T> contextClass, Logger logger) {
        this.contextClass = contextClass;
        this.logger = logger;
    }

    public T debug(String message) {
        return msg("DEBUG", message);
    }

    public T info(String message) {
        return msg("INFO", message);
    }

    public T error(String message) {
        return msg("ERROR", message);
    }

    private T msg(String level, String message) {
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
