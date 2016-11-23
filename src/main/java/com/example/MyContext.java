package com.example;

import org.ngmon.logger.injection.LogContext;
import org.ngmon.logger.annotation.VarContext;
import org.ngmon.logger.annotation.Var;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@VarContext
public class MyContext extends LogContext {

    @Var
    public MyContext ip(String string) {
        return this;
    }

    @Var
    public MyContext user(String string) {
        return this;
    }

    @Var
    public MyContext path(String string) {
        return this;
    }

    @Var
    public MyContext port(String string) {
        return this;
    }

    @Var
    public MyContext job(String string) {
        return this;
    }

    @Var
    public MyContext task(String string) {
        return this;
    }

    @Var
    public MyContext application(String string) {
        return this;
    }

    @Var
    public MyContext source(String string) {
        return this;
    }

    @Var
    public MyContext destination(String string) {
        return this;
    }

    @Var
    public MyContext exceptionList(List<Exception> exceptionList) {
        return this;
    }

    @Var
    public MyContext job_id(Integer integer) {
        return this;
    }

    public MyContext port(int integer) {
        return real_port(20*integer);
    }

    @Var
    public MyContext real_port(int integer) {
        return this;
    }

    public MyContext stacktrace(StackTraceElement[] stackTrace) {
        List<String> list = Arrays.stream(stackTrace).map(StackTraceElement::toString).collect(Collectors.toList());
        return stacktrace(list);
    }

    @Var
    public MyContext stacktrace(List<String> stackTrace) {
        return this;
    }

    @Var
    public MyContext exception(Exception e) {
        return stacktrace(e.getStackTrace());
    }
}
