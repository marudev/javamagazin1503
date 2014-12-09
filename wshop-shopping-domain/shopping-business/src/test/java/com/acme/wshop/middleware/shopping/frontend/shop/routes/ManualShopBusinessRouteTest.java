package com.acme.wshop.middleware.shopping.frontend.shop.routes;


import com.acme.wshop.middleware.shopping.business.shop.routes.ShopBusinessRoutes;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.custommonkey.xmlunit.XMLAssert;
import org.junit.Test;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertEquals;

/**
 * It is also quite easy to test camel manual (without framework support.)
 */
public class ManualShopBusinessRouteTest {

    @Test
    public void shouldCheckout() throws Exception {
        boolean hasPromotion = false;
        String basket = "<basket><item>A</item><item>B</item></basket>";
        CamelContext cc = createCamelContext(hasPromotion, basket);
        Object response = cc.createProducerTemplate().sendBody("vm:business:checkout", ExchangePattern.InOut, "<checkout/>");
        assertXMLEqual(basket, response.toString());
        cc.stop();
    }

    @Test
    public void shouldAddPromotionOnCheckout() throws Exception {
        boolean hasPromotion = true;
        String basket = "<basket><item>A</item><item>B</item></basket>";
        CamelContext cc = createCamelContext(hasPromotion, basket);
        Object response = cc.createProducerTemplate().sendBody("vm:business:checkout", ExchangePattern.InOut, "<checkout/>");
        assertXMLEqual("<basket><item>A</item><item>B</item><item>C</item></basket>", response.toString());
        cc.stop();
    }

    private CamelContext createCamelContext(final boolean hasPromotion, final String basket) throws Exception {
        CamelContext cc = new DefaultCamelContext();
        cc.addRoutes(new ShopBusinessRoutes());

        cc.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("vm:wshop-basket-backend.content").process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setBody(basket);
                    }
                });
                from("vm:wshop-promotions-backend.hasPromotion").process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        exchange.getIn().setHeader("hasPromotion", hasPromotion);
                        if (hasPromotion) {
                            exchange.getIn().setBody("C");
                        }
                    }
                });
            }
        });

        cc.start();
        return cc;
    }
}