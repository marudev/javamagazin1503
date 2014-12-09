package com.acme.wshop.integration;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

import java.io.IOException;

/**
 *
 * Support class for any kind of http integration testing.
 *
 * @author Samuel Marquis
 */
public class IntegrationTestSupport {

    private static final DefaultHttpClient httpClient;

    private static final String host = System.getProperty("shop.itest.hostname") != null ? System.getProperty("smx.hostname") : "localhost";


    static {
        PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
        httpClient = new DefaultHttpClient(cm);
    }

    protected HttpResponse doPost(String path, String body) throws IOException {
        HttpPost httpPost = new HttpPost("http://" + host + path);
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        StringEntity se = new StringEntity(body, "UTF-8");
        httpPost.setEntity(se);
        return httpClient.execute(httpPost);
    }

}
