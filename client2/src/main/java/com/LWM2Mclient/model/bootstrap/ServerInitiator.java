package com.LWM2Mclient.model.bootstrap;

import com.LWM2Mclient.model.entity.clientEntity.bootstrap.ServerInitiatedResponseBean;
import com.LWM2Mclient.model.entity.serverEntity.BootstrapDataBean;
import com.LWM2Mclient.model.utils.BootstrapPersistenceUtils;

/**
 * Created by ss on 2017/3/3.
 */
public class ServerInitiator {

    private String ERR_MESSAGE = "There is some error in process bootstrap.";
    private String SUCCESS_MESSAGE = "Bootstrap successfully.";

    public ServerInitiatedResponseBean initiate (BootstrapDataBean bootstrapDataBean) {

        ServerInitiatedResponseBean serverInitiatedResponseBean = new ServerInitiatedResponseBean();
        serverInitiatedResponseBean.initiateData();

        try {
            BootstrapPersistenceUtils.persist(bootstrapDataBean);
            serverInitiatedResponseBean.setBootResult(SUCCESS_MESSAGE);
        }
        catch (Exception e) {
            e.printStackTrace();
            serverInitiatedResponseBean.setBootResult(ERR_MESSAGE);
        }

        System.out.println(serverInitiatedResponseBean.getBootResult());

        return serverInitiatedResponseBean;
    }
}