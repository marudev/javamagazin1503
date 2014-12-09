package com.acme.wshop.integration;

import org.junit.Test;

import java.io.IOException;

/**
 * @author Samuel Marquis
 */
public class ShoppingTest extends ShoppingTestSupport {

    String USER_ID = "harrisb";


    @Test
    public void shouldAddPromotionToBasketWhenWhiskyBoughtWithGroceries() throws IOException {
        addToBasket(USER_ID, "whatever", 1);
        addToBasket(USER_ID, "Suntory 10", 1);
        Basket basket = proceedToCheckout(USER_ID);
        assertBasketContains(basket, "whatever", "Suntory Hibiki 17", "bottle");
    }

    private void assertBasketContains(Basket basket, String... itemsNames) {
        // TODO
    }

}
