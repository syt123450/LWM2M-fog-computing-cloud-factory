package com.LWM2Mclient;

import org.apache.http.client.fluent.Request;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ss on 2017/3/6.
 */
public class BootstrapHelper {

    String clientInitiatedBootstrapUrl = "http://localhost:8200/CMS/bootstrap/client";

    @Test
    @Ignore
    public void clientInitiatedBootstrap() throws Exception {

        String responseContent = Request.Get(clientInitiatedBootstrapUrl).execute().returnContent().asString();
        System.out.println(responseContent);
    }
}
