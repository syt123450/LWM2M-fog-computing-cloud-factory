package com.LWM2Mserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ss on 2017/3/6.
 */
public class CheckingHelper {

    private Gson gson = new GsonBuilder().create();

    String checkAllClientUrl = "http://localhost:8100/CMS/check/all";
    String checkOneClientUrl = "http://localhost:8100/CMS/check/one";

    String clientName = "client1";

    @Test
    @Ignore
    public void checkAllClient() throws Exception {

        String responseContent = Request.Get(checkAllClientUrl).execute().returnContent().asString();
        System.out.println(responseContent);
    }

    @Test
    @Ignore
    public void checkOneClient() throws Exception {

        CheckOneClientPostBody checkOneClientPostBody = new CheckOneClientPostBody();
        checkOneClientPostBody.setClientName(clientName);

        String message = gson.toJson(checkOneClientPostBody);

        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(checkOneClientUrl).body(httpEntity).execute().returnContent().asString();
        System.out.println(responseContent);
    }
}

@Data
class CheckOneClientPostBody {

    String clientName;
}