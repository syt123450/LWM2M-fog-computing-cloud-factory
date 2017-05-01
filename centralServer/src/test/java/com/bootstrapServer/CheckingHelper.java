package com.bootstrapServer;

import org.apache.http.client.fluent.Request;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ss on 2017/3/6.
 */
public class CheckingHelper {

    String checkBootstrapUrl = "http://localhost:8000/check/bootstrap";
    String checkClientsUrl = "http://localhost:8000/check/client";

    @Test
    @Ignore
    public void checkBootstrap() throws Exception {

        String responseContent = Request.Get(checkBootstrapUrl).execute().returnContent().asString();
        System.out.println(responseContent);
    }

    @Test
    @Ignore
    public void checkClients() throws Exception {

        String responseContent = Request.Get(checkClientsUrl).execute().returnContent().asString();
        System.out.println(responseContent);
    }
}
