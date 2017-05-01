package com.LWM2Mserver.model.registration;

import com.LWM2Mserver.model.entity.clientEntity.DeRegistrationBean;
import com.LWM2Mserver.model.entity.serverEntity.RegistrationResponseBean;
import com.LWM2Mserver.model.utils.MongodbUtils;

/**
 * Created by ss on 2017/3/3.
 */
public class DeKeygen {

    static private String ERROR_MESSAGE = "Client does not exist.";
    static private String SUCCESS_MESSAGE = "Successfully delete client.";

    public RegistrationResponseBean removeForServer(DeRegistrationBean deRegistrationBean) {

        String clientName = deRegistrationBean.getClientName();

        RegistrationResponseBean registrationResponseBean = new RegistrationResponseBean();
        registrationResponseBean.setOperation("DeRegistration");
        if (MongodbUtils.deleteClient(clientName) > 0) {
            MongodbUtils.deleteClientResources(clientName);
            registrationResponseBean.setInformation(SUCCESS_MESSAGE);
        } else {
            registrationResponseBean.setInformation(ERROR_MESSAGE);
        }

        return registrationResponseBean;
    }
}
