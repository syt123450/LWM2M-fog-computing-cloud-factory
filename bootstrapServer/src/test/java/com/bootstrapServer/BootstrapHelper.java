package com.bootstrapServer;

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
public class BootstrapHelper {

    private Gson gson = new GsonBuilder().create();

    String serverInitiatedBootstrapUrl = "http://localhost:8000/CMS/serverInitiated";
    String clientName = "client1";
    String serverName = "";

    @Test
    @Ignore
    public void bootstrap() throws Exception{

        BootstrapPostBody bootstrapPostBody = new BootstrapPostBody();
        bootstrapPostBody.setClientName(clientName);
        bootstrapPostBody.setServerName(serverName);

        String message = gson.toJson(bootstrapPostBody);

        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(serverInitiatedBootstrapUrl).body(httpEntity).execute().returnContent().asString();
        System.out.println(responseContent);
    }
}

@Data
class BootstrapPostBody {

    String clientName;
    String serverName;
}