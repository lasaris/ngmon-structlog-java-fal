package com.example;

import org.ngmon.logger.LogContext;
import org.ngmon.logger.PassThrough;

import java.util.List;

public class MyContext extends LogContext {

    public MyContext ip(String string) {
        return this;
    }

    public MyContext user(String string) {
        return this;
    }

    public MyContext path(String string) {
        return this;
    }

    public MyContext port(String string) {
        return this;
    }

    public MyContext job(String string) {
        return this;
    }

    public MyContext task(String string) {
        return this;
    }

    public MyContext application(String string) {
        return this;
    }

    public MyContext source(String string) {
        return this;
    }

    public MyContext destination(String string) {
        return this;
    }

    public MyContext exception(List<Exception> exceptionList) {
        return this;
    }

    public MyContext job_id(Integer integer) {
        return this;
    }

    @PassThrough()
    public MyContext port(int integer) {
        return real_port(20*integer);
    }

    public MyContext real_port(int integer) {
        return this;
    }
}
