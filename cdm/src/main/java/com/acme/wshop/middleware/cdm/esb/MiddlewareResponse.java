package com.acme.wshop.middleware.cdm.esb;

public class MiddlewareResponse<T> {

    public enum StatusCode {
        OK, ERROR
    }

    private final T content;
    public final StatusCode statusCode;

    public MiddlewareResponse(T content, StatusCode statusCode) {
        this.content = content;
        this.statusCode = statusCode;
    }

    public <U> U getContent(Class<U> type) {
        return (U)content;
    }
}
