package com.acme.wshop.middleware.cdm.shopping.shop;

public class Purchase {

    public final String userId;
    public final String productId;

    public Purchase(String userId, String productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public String toString() {
        return "Purchase [" + userId + ", " + productId + "]";
    }
}
