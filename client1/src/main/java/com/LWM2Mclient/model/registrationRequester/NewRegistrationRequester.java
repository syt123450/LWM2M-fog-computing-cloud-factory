package com.LWM2Mclient.model.registrationRequester;

import com.LWM2Mclient.model.entity.clientEntity.register.RegistrationResultBean;
import com.LWM2Mclient.model.entity.CMSEntity.NewRegistrationRequestBean;
import com.LWM2Mclient.model.entity.clientEntity.register.NewRegistrationBean;
import com.LWM2Mclient.model.entity.clientEntity.register.ResourceAddressBean;
import com.LWM2Mclient.model.entity.serverEntity.RegistrationResponseBean;
import com.LWM2Mclient.model.utils.MongodbUtils;
import com.LWM2Mclient.model.utils.MysqlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */
public class NewRegistrationRequester {

    static private String ERROR_Message = "ERROR happens in NewRegistration.";
    private Gson gson = new GsonBuilder().create();
    private Logger logger = Logger.getLogger(NewRegistrationRequester.class);

    public RegistrationResultBean register(NewRegistrationRequestBean newRegistrationRequestBean) {

        logger.info("In new register");

        String serverName = newRegistrationRequestBean.getServerName();
        String clientName = MysqlUtils.getClientName();
        RegistrationResultBean registrationResultBean = new RegistrationResultBean();
        registrationResultBean.setServerName(serverName);
        registrationResultBean.setClientName(clientName);

        try {
            String serverNewRegistrationUrl = MysqlUtils.getOneServerInfo(serverName, "newRegistrationUrl");

            logger.info("Register to server URL: " + serverNewRegistrationUrl);

            NewRegistrationBean newRegistrationBean = MysqlUtils.getClientInstanceInfo();
            ArrayList<ResourceAddressBean> resourceAddressList = MysqlUtils.getClientResourceInstanceInfo();
            newRegistrationBean.setResourceAddressList(resourceAddressList);

            String message = gson.toJson(newRegistrationBean);

            logger.info("New registration message is " + message);

            HttpEntity httpEntity = new StringEntity(message);
            String responseContent = Request.Post(serverNewRegistrationUrl).body(httpEntity).execute().returnContent().asString();

            RegistrationResponseBean registrationResponseBean = gson.fromJson(responseContent, RegistrationResponseBean.class);
            registrationResultBean.setRegistrationInfo(registrationResponseBean.getInformation());
        }
        catch (Exception e) {
            registrationResultBean.setRegistrationInfo(ERROR_Message);
        }

        return registrationResultBean;
    }
}
