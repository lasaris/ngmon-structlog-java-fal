package com.example;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.ngmon.logger.StructLog;

public class App {
    public static void main(String[] args) throws JsonMappingException {

        StructLog<MyContext> LOG = new StructLog<>(MyContext.class, new SimpleLogger());

        LOG.error("jesus").ip("192.168.0.1").ip("192.168.0.2").log();
        LOG.error("jesus1").ip("192.168.0.1").ip("192.168.0.2").log();
        LOG.info("jesus2").ip("192.168.0.1").ip("192.168.0.2").job_id(9999).log();
        LOG.error("jesus3").ip("192.168.0.1").ip("192.168.0.2").port(52).log();

    }
}