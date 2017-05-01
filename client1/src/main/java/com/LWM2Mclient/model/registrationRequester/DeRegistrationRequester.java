package com.LWM2Mclient.model.registrationRequester;

import com.LWM2Mclient.model.entity.CMSEntity.DeRegistrationRequestBean;
import com.LWM2Mclient.model.entity.clientEntity.register.DeRegistrationBean;
import com.LWM2Mclient.model.entity.clientEntity.register.RegistrationResultBean;
import com.LWM2Mclient.model.entity.serverEntity.RegistrationResponseBean;
import com.LWM2Mclient.model.utils.MongodbUtils;
import com.LWM2Mclient.model.utils.MysqlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;

/**
 * Created by ss on 2017/3/3.
 */

public class DeRegistrationRequester {

    private Gson gson = new GsonBuilder().create();
    static private String ERR_MESSAGE = "Error happens in DeRegistration.";

    public RegistrationResultBean delete(DeRegistrationRequestBean deRegistrationRequestBean) {

        String serverName = deRegistrationRequestBean.getServerName();
        String clientName = MysqlUtils.getClientName();
        RegistrationResultBean registrationResultBean = new RegistrationResultBean();
        registrationResultBean.setServerName(serverName);
        registrationResultBean.setClientName(clientName);

        try {
            String serverDeRegistrationUrl = MongodbUtils.getOneServerInfo(serverName, "deRegistrationUrl");

            DeRegistrationBean deRegistrationBean = new DeRegistrationBean();
            deRegistrationBean.setClientName(clientName);
            String message = gson.toJson(deRegistrationBean);

            HttpEntity httpEntity = new StringEntity(message);
            String responseContent = Request.Post(serverDeRegistrationUrl).body(httpEntity).execute().returnContent().asString();
            RegistrationResponseBean registrationResponseBean = gson.fromJson(responseContent, RegistrationResponseBean.class);
            registrationResultBean.setRegistrationInfo(registrationResponseBean.getInformation());
        } catch (Exception e) {
            registrationResultBean.setRegistrationInfo(ERR_MESSAGE);
        }

        return registrationResultBean;
    }
}
