package com.acme.wshop.middleware.shopping.frontend.shop.routes;

import com.acme.wshop.middleware.api.shopping.dto.PurchaseRequest;
import com.acme.wshop.middleware.api.shopping.dto.PurchaseResponse;
import com.acme.wshop.middleware.cdm.esb.MiddlewareRequest;
import com.acme.wshop.middleware.cdm.esb.MiddlewareResponse;
import com.acme.wshop.middleware.cdm.shopping.shop.ExecutedPurchase;
import com.acme.wshop.middleware.cdm.shopping.shop.Purchase;
import com.acme.middleware.framework.front.FrontendRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.springframework.stereotype.Component;
//
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component("shop-frontend.routes")
public class ShopFrontRoutes extends FrontendRouteBuilder {

    @Override
    public void configure() throws Exception {
//
        from("cxfrs:bean:rsServer?bindingStyle=SimpleConsumer")
                .filter().simple("${in.header.operationName} == 'buy'").to("direct:buy").end(); // TODO IMPROVE This

        from("direct:buy").id("shop-frontend-buy")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Message in = exchange.getIn();
                        PurchaseRequest body = in.getBody(PurchaseRequest.class);
                        Purchase purchase = new Purchase(body.getUserId(), body.getProductId());
                        System.out.println(purchase.toString());
                        exchange.getOut().setBody(new MiddlewareRequest(purchase));
                    }
                })
                .to("vm:wshop-store-backend.buy")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Message in = exchange.getIn();
                        PurchaseResponse resp = new PurchaseResponse();
                        MiddlewareResponse mr = in.getBody(MiddlewareResponse.class);
                        ExecutedPurchase ep = (ExecutedPurchase) mr.getContent(ExecutedPurchase.class); // TODO remove cast
                        resp.setPurchaseId(ep.id);
                        exchange.getOut().setBody(Response.ok().entity(resp).type(MediaType.APPLICATION_JSON).build());
                    }
                });
    }
}
