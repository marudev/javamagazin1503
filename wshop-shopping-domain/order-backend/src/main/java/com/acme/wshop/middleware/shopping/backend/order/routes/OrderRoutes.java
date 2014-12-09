package com.acme.wshop.middleware.shopping.backend.order.routes;


import com.acme.wshop.middleware.cdm.esb.MiddlewareRequest;
import com.acme.wshop.middleware.cdm.esb.MiddlewareResponse;
import com.acme.wshop.middleware.cdm.shopping.shop.ExecutedPurchase;
import com.acme.wshop.middleware.cdm.shopping.shop.Purchase;
import com.acme.middleware.framework.backend.BackendRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component("order.backend.routes")
public class OrderRoutes extends BackendRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("vm:wshop-order-backend.buy").id("order-backend.buy")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Message in = exchange.getIn();
                        in.getHeaders().put("processedBy", "wshop-order-backend");
                        MiddlewareRequest<Purchase> body = in.getBody(MiddlewareRequest.class);
                        Purchase purchase = body.getContent();

                        //
                        // do something with purchase, translate it into the partner's entity
                        //

                        ExecutedPurchase ep = new ExecutedPurchase(purchase.userId, purchase.userId, "70111");
                        MiddlewareResponse mr = new MiddlewareResponse(ep, MiddlewareResponse.StatusCode.OK);
                        exchange.getOut().setBody(mr);
                    }
                });
    }
}
