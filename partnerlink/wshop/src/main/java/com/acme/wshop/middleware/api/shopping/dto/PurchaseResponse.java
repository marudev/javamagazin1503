package com.acme.wshop.middleware.api.shopping.dto;


import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonPropertyOrder({"purchaseId"})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PurchaseResponse {

    private String purchaseId;

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }
}
