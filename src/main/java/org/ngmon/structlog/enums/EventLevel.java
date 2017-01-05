package org.ngmon.structlog.enums;

public enum EventLevel {
        DEBUG("DEBUG"),
        ERROR("ERROR"),
        INFO("INFO");

        private final String value;

        EventLevel(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }