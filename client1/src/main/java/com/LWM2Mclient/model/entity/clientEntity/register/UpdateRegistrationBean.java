package com.LWM2Mclient.model.entity.clientEntity.register;

import com.LWM2Mclient.model.entity.clientEntity.operation.ResourceUpdateBean;
import com.LWM2Mclient.model.utils.PropertiesUtils;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when client is doing updateRegistration, this is the dateType client sends to server
 */

@Data
public class UpdateRegistrationBean {

    private String clientName;
    private String lifeTime;
    private String version;
    private String bindingMode;
    private ArrayList<ResourceUpdateBean> resourceUpdateBeans;

    public void initiateData() {
        clientName = PropertiesUtils.getProperty("name");
    }
}
