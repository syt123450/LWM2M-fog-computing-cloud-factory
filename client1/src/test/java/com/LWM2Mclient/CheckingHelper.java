package com.LWM2Mclient;

import org.apache.http.client.fluent.Request;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ss on 2017/3/6.
 */
public class CheckingHelper {

    String checkBootstrapUrl = "http://localhost:8200/CMS/check/bootstrap";

    @Test
    @Ignore
    public void checkBootstrapInfo() throws Exception {

        String responseContent = Request.Get(checkBootstrapUrl).execute().returnContent().asString();
        System.out.println(responseContent);
    }
}
