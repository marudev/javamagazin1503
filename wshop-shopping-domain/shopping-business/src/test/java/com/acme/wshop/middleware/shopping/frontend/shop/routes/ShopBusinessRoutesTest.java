package com.acme.wshop.middleware.shopping.frontend.shop.routes;

import com.acme.wshop.middleware.shopping.business.shop.routes.ShopBusinessRoutes;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

/**
 * Using CamelTestSupport
 *
 * @author Samuel Marquis
 */
public class ShopBusinessRoutesTest extends CamelTestSupport {

    // we need to mock the 2 backend modules for this example
    @Before
    public void mockBackendRoutes() throws Exception {
        AdviceWithRouteBuilder advice = new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("vm:wshop-basket-backend.content")
                        .skipSendToOriginalEndpoint()
                        .process(new Processor() {
                            // basket backend will always return a basket with items A and B
                            public void process(Exchange exchange) throws Exception {
                                exchange.getIn().setBody("<basket><item>A</item><item>B</item></basket>");
                            }
                        });
                interceptSendToEndpoint("vm:wshop-promotions-backend.hasPromotion")
                        .skipSendToOriginalEndpoint()
                        .process(new Processor() {
                            // promotion backend always promotes item C
                            public void process(Exchange exchange) throws Exception {
                                exchange.getIn().setHeader("hasPromotion", true);
                                exchange.getIn().setBody("C");
                            }
                        });
            }
        };
        context.getRouteDefinition("business-checkout").adviceWith(context, advice);
    }
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new ShopBusinessRoutes(); // this builds the production routes under test
    }

    // now the test case
    @Test
    public void shouldAddPromotionOnCheckout() throws Exception {
        String routeUnderTest = "vm:business:checkout";
        Object response = template.sendBody(routeUnderTest, ExchangePattern.OutIn, "<checkout/>");
        // given the mock setup, our route should have added C to the basket; let us assert this:
        assertXMLEqual("<basket><item>A</item><item>B</item><item>C</item></basket>", response.toString());
    }
}
