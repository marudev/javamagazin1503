package com.acme.wshop.middleware.shopping.business.shop.routes;

import com.acme.middleware.framework.business.BusinessRouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.springframework.stereotype.Component;

@Component("shop-business.routes")
public class ShopBusinessRoutes extends BusinessRouteBuilder {

    @Override
    public void configure() throws Exception {

        from("vm:business:checkout").id("business-checkout")
                // first, create the message for the Basket service and send it
                .to("xslt:getBasketContentMessage.xsl")
                .to("vm:wshop-basket-backend.content")
                .setHeader("basket-content", body()) // save the body for later
                // now, create the message for the Promotion service and send it
                .to("xslt:hasPromotionsForBasketMessage.xsl")
                .to("vm:wshop-promotions-backend.hasPromotion")

                .choice() // this is where some piece of business/integration logic is implemented
                    .when(hasPromotions())
                        .setHeader("promotion", body())
                        .setBody(header("basket-content"))
                        .to("xslt:basketWithPromotionMessage.xsl") // add promotion to original basket
                    .otherwise()
                        // just resend the original basket
                        .setBody(header("basket-content"))
                .end();

        from("vm:business:orderItems").id("business-orderItems")
                .to("xslt:orderMessage.xsl")
                .to("vm:wshop-basket-backend-order.order");

        from("vm:business:addToBasket").id("business-addToBasket")
                .to("xslt:addToBasketMessage.xsl")
                .to("vm:wshop-basket-backend.add");

    }

    private Predicate hasPromotions() {
        return new Predicate() {
            @Override
            public boolean matches(Exchange exchange) {
                return exchange.getIn().getHeader("hasPromotion").equals(Boolean.TRUE);
            }
        };
    }
}
