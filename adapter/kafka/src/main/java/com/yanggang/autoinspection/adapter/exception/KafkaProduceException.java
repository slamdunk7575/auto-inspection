package com.yanggang.autoinspection.adapter.exception;

public class KafkaProduceException extends RuntimeException {
    public KafkaProduceException(String message, Throwable cause) {
        super(message, cause);
    }
}
