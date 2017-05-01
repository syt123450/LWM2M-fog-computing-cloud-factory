package com.LWM2Mserver.model.checker;


import com.LWM2Mserver.model.entity.clientEntity.ResourceAddressBean;
import com.LWM2Mserver.model.entity.serverEntity.ClientInfoBean;
import com.LWM2Mserver.model.entity.CMSEntity.CheckOneRequestBean;
import com.LWM2Mserver.model.utils.MongodbUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */
public class ClientInfoChecker {

    private Logger logger = Logger.getLogger(ClientInfoChecker.class);

    public ClientInfoBean getOneClient(CheckOneRequestBean checkOneRequestBean) {

        String clientName = checkOneRequestBean.getClientName();
        ClientInfoBean clientInfoBean = MongodbUtils.getClientInfo(clientName);
        clientInfoBean.setResourceAddressList(MongodbUtils.getClientResources(clientName));
        return clientInfoBean;
    }

    public ArrayList<ClientInfoBean> getAllClient() {

        ArrayList<ClientInfoBean> clientInfoList = MongodbUtils.getAllClientsInfo();

        logger.info("Client info list is " + clientInfoList);

        for (int i = 0; i < clientInfoList.size(); i++) {
            String clientName = clientInfoList.get(i).getClientName();
            ArrayList<ResourceAddressBean> resourceAddressList = MongodbUtils.getClientResources(clientName);
            clientInfoList.get(i).setResourceAddressList(resourceAddressList);
        }

        return clientInfoList;
    }
}