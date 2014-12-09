package com.acme.wshop.middleware.api.shopping.interfaces;

import com.acme.wshop.middleware.api.shopping.dto.PurchaseRequest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class EshopService {

    @POST
    @Path("/purchase")
    public Response buy(PurchaseRequest purchase) {
        return null;
    }
}
