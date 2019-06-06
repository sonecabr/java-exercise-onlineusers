package com.thinkstep.test.onlineusers.log.collector;

public class LogFileProcessException extends Exception {
    public LogFileProcessException() {
    }

    public LogFileProcessException(String message) {
        super(message);
    }

    public LogFileProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogFileProcessException(Throwable cause) {
        super(cause);
    }

    public LogFileProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
