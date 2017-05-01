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
public class ManagementHelper {

    private Gson gson = new GsonBuilder().create();

    String deleteServerUrl = "http://localhost:8000/CMS/serverManage/delete";
    String createServerUrl = "http://localhost:8000/CMS/serverManage/add";

    String serverName = "server1";
    String newRegistrationUrl = "http://localhost:8100/register/newRegister";
    String updateRegistrationUrl = "http://localhost:8100/register/updateRegister";
    String deRegistrationUrl = "http://localhost:8100/register/deRegister";

    @Test
    @Ignore
    public void deleteServer() throws Exception{

        DeleteServerPostBody deleteServerPostBody = new DeleteServerPostBody();
        deleteServerPostBody.setServerName(serverName);

        String message = gson.toJson(deleteServerPostBody);

        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(deleteServerUrl).body(httpEntity).execute().returnContent().asString();
        System.out.println(responseContent);

    }

    @Test
    @Ignore
    public void addServer() throws Exception {

        CreateServerPostBody createServerPostBody = new CreateServerPostBody();
        createServerPostBody.setServerName(serverName);
        createServerPostBody.setNewRegistrationUrl(newRegistrationUrl);
        createServerPostBody.setUpdateRegistrationUrl(updateRegistrationUrl);
        createServerPostBody.setDeRegistrationUrl(deRegistrationUrl);

        String message = gson.toJson(createServerPostBody);

        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(createServerUrl).body(httpEntity).execute().returnContent().asString();
        System.out.println(responseContent);

    }
}

@Data
class DeleteServerPostBody {

    String serverName;
}

@Data
class CreateServerPostBody {

    String serverName;
    String newRegistrationUrl;
    String updateRegistrationUrl;
    String deRegistrationUrl;
}
