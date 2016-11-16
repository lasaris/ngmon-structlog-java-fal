package com.example;

import org.ngmon.logger.Log;

import java.util.IllegalFormatException;

public class App {
    public static void main(String[] args) {

        Log<MyContext> LOG = new Log<>(MyContext.class, new SimpleLogger());

        LOG.debug("hello").job_id(456).job("yiipp").log();
        LOG.error("Eagle has landed!").application("ThisAPP").port(554).log();

    }
}