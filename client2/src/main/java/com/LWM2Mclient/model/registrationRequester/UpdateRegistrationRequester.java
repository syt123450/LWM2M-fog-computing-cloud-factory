package com.LWM2Mclient.model.registrationRequester;

import com.LWM2Mclient.model.entity.CMSEntity.UpdateRequestBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.ResourceUpdateBean;
import com.LWM2Mclient.model.entity.clientEntity.register.RegistrationResultBean;
import com.LWM2Mclient.model.entity.CMSEntity.UpdateRegistrationRequestBean;
import com.LWM2Mclient.model.entity.clientEntity.register.UpdateRegistrationBean;
import com.LWM2Mclient.model.entity.serverEntity.RegistrationResponseBean;
import com.LWM2Mclient.model.utils.MysqlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ss on 2017/3/3.
 */
public class UpdateRegistrationRequester {

    static private String ERROR_Message = "ERROR happens in UpdateRegistration.";
    private Gson gson = new GsonBuilder().create();
    private Logger logger = Logger.getLogger(UpdateRegistrationRequester.class);

    public RegistrationResultBean update(UpdateRegistrationRequestBean updateRegistrationRequestBean) {

        String serverName = updateRegistrationRequestBean.getServerName();
        String clientName = MysqlUtils.getClientName();
        RegistrationResultBean registrationResultBean = new RegistrationResultBean();
        registrationResultBean.setServerName(serverName);
        registrationResultBean.setClientName(clientName);

        try {
            String serverUpdateRegistrationUrl = MysqlUtils.getOneServerInfo(serverName, "updateRegistrationUrl");

            logger.info("Update to server who has the update url: " + serverUpdateRegistrationUrl);

            UpdateRegistrationBean updateRegistrationBean = new UpdateRegistrationBean();
            updateRegistrationBean.initiateData();
            updateRegistrationBean.setLifeTime(updateRegistrationRequestBean.getLifeTime());
            updateRegistrationBean.setVersion(updateRegistrationRequestBean.getVersion());
            updateRegistrationBean.setBindingMode(updateRegistrationRequestBean.getBindingMode());

            ArrayList<ResourceUpdateBean> resourceUpdateBeans = createResourceUpdateBeans(updateRegistrationRequestBean.getUpdateRequestBeans());
            updateRegistrationBean.setResourceUpdateBeans(resourceUpdateBeans);

            logger.info("Update data sent to server is: " + updateRegistrationBean);

            MysqlUtils.updateRegistrationInfo(updateRegistrationBean);

            String message = gson.toJson(updateRegistrationBean);

            HttpEntity httpEntity = new StringEntity(message);
            String responseContent = Request.Post(serverUpdateRegistrationUrl).body(httpEntity).execute().returnContent().asString();
            RegistrationResponseBean registrationResponseBean = gson.fromJson(responseContent, RegistrationResponseBean.class);

            System.out.println(registrationResponseBean);
            registrationResultBean.setRegistrationInfo(registrationResponseBean.getInformation());
        } catch (Exception e) {
            registrationResultBean.setRegistrationInfo(ERROR_Message);
        }

        return registrationResultBean;
    }

    private ArrayList<ResourceUpdateBean> createResourceUpdateBeans(ArrayList<UpdateRequestBean> updateRequestBeans) {

        ArrayList<ResourceUpdateBean> resourceUpdateBeans = new ArrayList<>();

        for (int i = 0; i < updateRequestBeans.size(); i++) {
            UpdateRequestBean updateRequestBean = updateRequestBeans.get(i);
            int resourceID = updateRequestBean.getResourceID();
            String operation = updateRequestBean.getOperation();
            HashMap<String, String> resourceInfo = MysqlUtils.getResourceOperationInfo(resourceID, operation);

            ResourceUpdateBean resourceUpdateBean = new ResourceUpdateBean();
            resourceUpdateBean.setResourceID(resourceID);
            resourceUpdateBean.setOperation(operation);
            resourceUpdateBean.setResourceSwitch(updateRequestBean.getResourceSwitch());
            resourceUpdateBean.setResourceName(resourceInfo.get("resourceName"));
            resourceUpdateBean.setAddress(resourceInfo.get("address"));

            resourceUpdateBeans.add(resourceUpdateBean);
        }

        return resourceUpdateBeans;
    }
}