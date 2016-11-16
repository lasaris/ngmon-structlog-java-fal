package com.example;

import org.ngmon.logger.LogContext;

public class MyContext extends LogContext {

    public MyContext ip(String ip) {
        return this;
    }

    public MyContext user(String user) {
        return this;
    }

    public MyContext path(String path) {
        return this;
    }

    public MyContext port(String port) {
        return this;
    }

    public MyContext job(String job) {
        return this;
    }

    public MyContext task(String task) {
        return this;
    }

    public MyContext application(String application) {
        return this;
    }

    public MyContext source(String source) {
        return this;
    }

    public MyContext destination(String destination) {
        return this;
    }

    public MyContext exception(Exception exception) {
        return this;
    }

    public MyContext job_id(int job_id) {
        return this;
    }

    public MyContext port(int i) {
        return this;
    }
}
