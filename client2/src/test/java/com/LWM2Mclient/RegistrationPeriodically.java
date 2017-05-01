package com.LWM2Mclient;

import com.LWM2Mclient.model.entity.clientEntity.register.NewRegistrationBean;
import com.LWM2Mclient.model.entity.serverEntity.RegistrationResponseBean;
import com.LWM2Mclient.model.utils.MongodbUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

/**
 * Created by ss on 2017/3/6.
 */
public class RegistrationPeriodically {

    private Gson gson = new GsonBuilder().create();
    private String serverName = "server1";

    @Test
    public void register() throws Exception{

        while (true) {

            try{
                String serverNewRegistrationUrl = MongodbUtils.getOneServerInfo(serverName, "newRegistrationUrl");
                NewRegistrationBean newRegistrationBean = MongodbUtils.getNewRegistrationData();
                String message = gson.toJson(newRegistrationBean);

                HttpEntity httpEntity = new StringEntity(message);
                String responseContent = Request.Post(serverNewRegistrationUrl).body(httpEntity).execute().returnContent().asString();

                RegistrationResponseBean registrationResponseBean = gson.fromJson(responseContent, RegistrationResponseBean.class);
                System.out.println("Registration Successfully");
            }
            catch (Exception e) {
                System.out.println("Some error happens in registration");
            }

            Thread.sleep(600000);
        }
    }
}
