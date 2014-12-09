package com.acme.wshop.middleware.cdm.shopping.shop;

public class ExecutedPurchase extends Purchase {

    public final String id;

    public ExecutedPurchase(String userId, String productId, String id) {
        super(userId, productId);
        this.id = id;
    }
}
