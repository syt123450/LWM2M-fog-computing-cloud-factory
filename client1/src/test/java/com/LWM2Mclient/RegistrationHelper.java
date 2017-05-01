package com.LWM2Mclient;

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
public class RegistrationHelper {

    private Gson gson = new GsonBuilder().create();

    String newRegistrationUrl = "http://localhost:8200/CMS/register/new";
    String updateRegistrationUrl = "http://localhost:8200/CMS/register/update";
    String deRegistrationUrl = "http://localhost:8200/CMS/register/delete";

    String serverName = "server1";
    String lifeTime = "86400";
    String version = "1.0";
    String bindingMode = "U";
    String objectInstance = "<1><2><3><4><5>";


    @Test
    @Ignore
    public void newRegister() throws Exception {

        NewRegisterPostBody newRegisterPostBody = new NewRegisterPostBody();
        newRegisterPostBody.setServerName(serverName);

        String message = gson.toJson(newRegisterPostBody);

        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(newRegistrationUrl).body(httpEntity).execute().returnContent().asString();
        System.out.println(responseContent);

    }

    @Test
    @Ignore
    public void updateRegister() throws Exception {

        UpdateRegisterPostBody updateRegisterPostBody = new UpdateRegisterPostBody();
        updateRegisterPostBody.setServerName(serverName);
        updateRegisterPostBody.setObjectInstance(objectInstance);
        updateRegisterPostBody.setLifeTime(lifeTime);
        updateRegisterPostBody.setBindingMode(bindingMode);
        updateRegisterPostBody.setVersion(version);

        String message = gson.toJson(updateRegisterPostBody);

        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(updateRegistrationUrl).body(httpEntity).execute().returnContent().asString();
        System.out.println(responseContent);
    }

    @Test
    @Ignore
    public void deRegister() throws Exception {

        DeRegisterPostBody deRegisterPostBody = new DeRegisterPostBody();
        deRegisterPostBody.setServerName(serverName);

        String message = gson.toJson(deRegisterPostBody);

        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(deRegistrationUrl).body(httpEntity).execute().returnContent().asString();
        System.out.println(responseContent);
    }
}

@Data
class NewRegisterPostBody {

    String serverName;
}

@Data
class UpdateRegisterPostBody {

    String serverName;
    String lifeTime;
    String version;
    String bindingMode;
    String objectInstance;
}

@Data
class DeRegisterPostBody {

    String serverName;
}
