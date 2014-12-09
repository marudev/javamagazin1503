package com.acme.wshop.integration;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.List;

/**
 * Support class for testing the Shopping domain of wshop.
 *
 * @author Samuel Marquis
 */
public class ShoppingTestSupport extends IntegrationTestSupport {

    public static class Basket {
        public List<Item> items;

        public static Basket fromResponse(HttpResponse httpResponse) {
            // TODO: build Cart for httpResponse
            return null;
        }
    }

    public static class Item {
        public Item(String name, int quantity, float price) {
            // ...
        }
    }

    protected void addToBasket(String userId, String itemId, int qty) throws IOException {
        String json = newMap().with("user", userId).with("item", itemId).with("quantity", qty).toJson();
        doPost("/basket", json);
    }

    protected Basket proceedToCheckout(String userId) throws IOException {
        HttpResponse httpResponse = doPost("/checkout", newMap().with("user", userId).toJson());
        return Basket.fromResponse(httpResponse);
    }

    private static class Map {
        public Map with(String key, Object value) {
            return null; // ...
        }
        public String toJson() {
            return null;
        }
    }

    private Map newMap() {
        return new Map();
    }


}
