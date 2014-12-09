package com.acme.wshop.middleware.api.shopping.dto;


import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonPropertyOrder({"userId", "productId"})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)

public class PurchaseRequest {

    private String userId;
    private String productId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
