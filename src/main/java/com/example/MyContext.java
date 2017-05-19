package com.example;

import org.ngmon.structlog.annotation.Var;
import org.ngmon.structlog.annotation.VarContext;
import org.ngmon.structlog.injection.VariableContext;

import java.util.List;

@VarContext
public class MyContext extends VariableContext {

    @Var
    public MyContext ip(String string) {
        this.inject("ip", string);
        return this;
    }

    @Var
    public MyContext user(String string) {
        this.inject("user", string);
        return this;
    }

    @Var
    public MyContext path(String string) {
        this.inject("path", string);
        return this;
    }

    @Var
    public MyContext port(String string) {
        this.inject("port", string);
        return this;
    }

    @Var
    public MyContext job(String string) {
        this.inject("job", string);
        return this;
    }

    @Var
    public MyContext task(String string) {
        this.inject("task", string);
        return this;
    }

    @Var
    public MyContext application(String string) {
        this.inject("application", string);
        return this;
    }

    @Var
    public MyContext source(String string) {
        this.inject("source", string);
        return this;
    }

    @Var
    public MyContext destination(String string) {
        this.inject("destination", string);
        return this;
    }

    @Var
    public MyContext exceptionList(List<Double> exceptionList) {
        this.inject("exceptionList", exceptionList);
        return this;
    }

    @Var
    public MyContext job_id(Integer integer) {
        this.inject("job_id", integer);
        return this;
    }

    public MyContext port(Integer integer) {
        return real_port(20*integer);
    }

    @Var
    public MyContext real_port(Integer integer) {
        this.inject("real_port", integer);
        return this;
    }

    @Var
    public MyContext stacktrace(List<String> stackTrace) {
        this.inject("stacktrace", stackTrace);
        return this;
    }

}
