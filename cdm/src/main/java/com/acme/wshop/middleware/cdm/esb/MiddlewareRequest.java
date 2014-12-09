package com.acme.wshop.middleware.cdm.esb;

public class MiddlewareRequest<T> {

    private final T content;

    public MiddlewareRequest(T content) {
        this.content = content;
    }

    public T getContent() {
        return content;
    }
}
