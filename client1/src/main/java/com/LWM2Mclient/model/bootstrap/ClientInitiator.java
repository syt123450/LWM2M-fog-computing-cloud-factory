package com.LWM2Mclient.model.bootstrap;

import com.LWM2Mclient.model.entity.clientEntity.bootstrap.CMSBootstrapResultBean;
import com.LWM2Mclient.model.entity.serverEntity.BootstrapDataBean;
import com.LWM2Mclient.model.entity.clientEntity.bootstrap.BootstrapRequestBean;
import com.LWM2Mclient.model.utils.BootstrapPersistenceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;

/**
 * Created by ss on 2017/3/3.
 */
public class ClientInitiator {

    private String BOOTSTRAP_URL = "http://localhost:8000/bootstrap/clientInitiated";
    private String ERR_MESSAGE = "There is some error in process bootstrap.";
    private String SUCCESS_MESSAGE = "Bootstrap successfully.";
    private Gson gson = new GsonBuilder().create();

    public CMSBootstrapResultBean initiated() {

        CMSBootstrapResultBean CMSBootstrapResultBean = new CMSBootstrapResultBean();
        BootstrapRequestBean bootstrapRequestBean = new BootstrapRequestBean();
        bootstrapRequestBean.initiateData();

        try {
            String message = gson.toJson(bootstrapRequestBean);
            HttpEntity httpEntity = new StringEntity(message);
            String responseContent = Request.Post(BOOTSTRAP_URL).body(httpEntity).execute().returnContent().asString();
            BootstrapDataBean bootstrapDataBean = gson.fromJson(responseContent, BootstrapDataBean.class);
            BootstrapPersistenceUtils.persist(bootstrapDataBean);
            if (bootstrapDataBean.getInformation() == null) {
                CMSBootstrapResultBean.setInformation(SUCCESS_MESSAGE);
            } else {
                CMSBootstrapResultBean.setInformation(bootstrapDataBean.getInformation());
            }
        } catch (Exception e) {
            CMSBootstrapResultBean.setInformation(ERR_MESSAGE);
        }

        return CMSBootstrapResultBean;
    }
}