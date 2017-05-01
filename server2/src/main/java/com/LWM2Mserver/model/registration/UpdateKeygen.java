package com.LWM2Mserver.model.registration;

import com.LWM2Mserver.model.entity.clientEntity.ResourceUpdateBean;
import com.LWM2Mserver.model.entity.clientEntity.UpdateRegistrationBean;
import com.LWM2Mserver.model.entity.serverEntity.RegistrationResponseBean;
import com.LWM2Mserver.model.utils.MongodbUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ss on 2017/3/3.
 */

public class UpdateKeygen {

    static private String SUCCESS_MESSAGE = "Successfully update.";
    static private String ERROR_MESSAGE = "The client does not exist, please register first.";

    public RegistrationResponseBean updateClientInfo(UpdateRegistrationBean updateRegistrationBean) {

        RegistrationResponseBean registrationResponseBean = new RegistrationResponseBean();
        registrationResponseBean.setOperation("updateRegistration");

        String clientName = updateRegistrationBean.getClientName();

        if (!MongodbUtils.isClientExist(clientName)) {
            registrationResponseBean.setInformation(ERROR_MESSAGE);
            return registrationResponseBean;
        }

        updateClientInstance(updateRegistrationBean);
        updateClientResourceInstance(updateRegistrationBean);

        registrationResponseBean.setInformation(SUCCESS_MESSAGE);

        return registrationResponseBean;
    }

    private void updateClientResourceInstance(UpdateRegistrationBean updateRegistrationBean) {
        ArrayList<ResourceUpdateBean> resourceUpdateBeans = updateRegistrationBean.getResourceAddressList();
        if (resourceUpdateBeans != null) {
            for (int i = 0; i < resourceUpdateBeans.size(); i++) {
                ResourceUpdateBean resourceUpdateBean = resourceUpdateBeans.get(i);
                if (resourceUpdateBean.getOperation().equals("ON")) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("clientName", updateRegistrationBean.getClientName());
                    hashMap.put("resourceID", resourceUpdateBean.getResourceID());
                    hashMap.put("resourceName", resourceUpdateBean.getResourceName());
                    hashMap.put("operation", resourceUpdateBean.getOperation());
                    hashMap.put("address", resourceUpdateBean.getAddress());
                    MongodbUtils.insertResourceOperation(hashMap);
                }
                else if (resourceUpdateBean.getOperation().equals("OFF")) {
                    MongodbUtils.deleteResourceOperation(updateRegistrationBean.getClientName(),
                            String.valueOf(resourceUpdateBean.getResourceID()),
                            resourceUpdateBean.getOperation());
                }
            }
        }

    }

    private void updateClientInstance(UpdateRegistrationBean updateRegistrationBean) {
        String clientName = updateRegistrationBean.getClientName();
        String lifeTime = updateRegistrationBean.getLifeTime();
        String version = updateRegistrationBean.getVersion();
        String bindingMode = updateRegistrationBean.getBindingMode();

        HashMap<String, String> updateClientInfo = new HashMap<>();
        updateClientInfo.put("clientName", clientName);
        if (!lifeTime.equals("")) {
            updateClientInfo.put("lifeTime", lifeTime);
            updateClientInfo.put("timeStamp", String.valueOf(new Date().getTime()));
        }
        if (!version.equals("")) {
            updateClientInfo.put("version", version);
        }
        if (!bindingMode.equals("")) {
            updateClientInfo.put("bindingMode", bindingMode);
        }

        MongodbUtils.updateClient(clientName, updateClientInfo);
    }
}
