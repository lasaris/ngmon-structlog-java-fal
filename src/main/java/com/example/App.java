package com.example;

import org.ngmon.logger.Log;

import java.io.IOException;
import java.util.Arrays;

public class App {
    public static void main(String[] args) {

        Log<MyContext> LOG = new Log<>(MyContext.class, new SimpleLogger());

        try {
            throw new Exception("The input/output has failed!");
        } catch (Exception e) {
            LOG.error(e.getClass().getSimpleName() + e.getMessage()).stacktrace(e.getStackTrace()).log();
            LOG.info("Whoops..").exception(e).log();
        }

        LOG.debug("DebugMessage job").job_id(456).job("hadoop").log();
        LOG.error("Eagle has landed!").application("ThisAPP").port(554).log();
        LOG.error("Gregzo").exceptionList(Arrays.asList(new RuntimeException("Whee"), new IllegalArgumentException("Deee"))).log();
        LOG.info("Just Message").log();
        LOG.info("hello").job("yiipp").job_id(456).log();

    }
}