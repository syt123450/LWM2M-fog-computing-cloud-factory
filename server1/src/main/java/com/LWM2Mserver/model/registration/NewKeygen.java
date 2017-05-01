package com.LWM2Mserver.model.registration;

import com.LWM2Mserver.model.entity.clientEntity.NewRegistrationBean;
import com.LWM2Mserver.model.entity.clientEntity.ResourceAddressBean;
import com.LWM2Mserver.model.entity.serverEntity.RegistrationResponseBean;
import com.LWM2Mserver.model.utils.MongodbUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ss on 2017/3/3.
 */
public class NewKeygen {

    static private String SUCCESS_MESSAGE = "Successfully register.";
    static private String ERROR_MESSAGE = "";

    public RegistrationResponseBean registerToServer(NewRegistrationBean newRegistrationBean) {

        String clientName = newRegistrationBean.getClientName();
        if (MongodbUtils.isClientExist(clientName)) {
            MongodbUtils.deleteClient(clientName);
        }

        HashMap newClientInfo = new HashMap();
        newClientInfo.put("clientName", clientName);
        newClientInfo.put("lifeTime", newRegistrationBean.getLifeTime());
        newClientInfo.put("version", newRegistrationBean.getVersion());
        newClientInfo.put("bindingMode", newRegistrationBean.getBindingMode());
        newClientInfo.put("address", newRegistrationBean.getAddress());
        newClientInfo.put("timeStamp", String.valueOf(new Date().getTime()));

        MongodbUtils.insertClient(newClientInfo);

        ArrayList<ResourceAddressBean> resourceAddressList = newRegistrationBean.getResourceAddressList();

        for (int i = 0; i < resourceAddressList.size(); i++) {

            ResourceAddressBean resourceAddressBean = resourceAddressList.get(i);

            String resourceID = String.valueOf(resourceAddressBean.getResourceID());
            String resourceName = resourceAddressBean.getResourceName();
            String operation = resourceAddressBean.getOperation();
            String address = resourceAddressBean.getAddress();

            if (MongodbUtils.isResourceOperationExist(clientName, resourceID, operation)) {
                MongodbUtils.deleteResourceOperation(clientName, resourceID, operation);
            }

            HashMap newResourceInfo = new HashMap();
            newResourceInfo.put("clientName", clientName);
            newResourceInfo.put("resourceID", resourceID);
            newResourceInfo.put("resourceName", resourceName);
            newResourceInfo.put("operation", operation);
            newResourceInfo.put("address", address);

            MongodbUtils.insertResourceOperation(newResourceInfo);
        }

        RegistrationResponseBean registrationResponseBean = new RegistrationResponseBean();
        registrationResponseBean.setOperation("NewRegistration");
        registrationResponseBean.setInformation(SUCCESS_MESSAGE);

        return registrationResponseBean;
    }
}
