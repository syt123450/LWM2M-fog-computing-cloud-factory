package com.bootstrapServer.model.bootstrap;

import com.bootstrapServer.model.entity.serverEntity.ServerInfoBean;
import com.bootstrapServer.model.entity.serverEntity.BootstrapResponseBean;
import com.bootstrapServer.model.entity.clientEntity.UsrBootstrapRequestBean;
import com.bootstrapServer.model.utils.MongodbUtils;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */
public class ClientInitiator {

    public BootstrapResponseBean initiate(UsrBootstrapRequestBean usrBootstrapRequestBean) {

        BootstrapResponseBean bootstrapResponseBean = new BootstrapResponseBean();
        String clientName = usrBootstrapRequestBean.getClientName();
        if (MongodbUtils.checkClient(clientName)) {
            ArrayList<ServerInfoBean> bootstrapDataList =  MongodbUtils.getAllBootstrapData();
            bootstrapResponseBean.setWrite(true);
            bootstrapResponseBean.setWriteList(bootstrapDataList);
            bootstrapResponseBean.setDelete("All");
            bootstrapResponseBean.setDeleteList(null);
            bootstrapResponseBean.setCentralServer(true);
            bootstrapResponseBean.setCentralServerInfoBean(MongodbUtils.getCentralServerInfo());
        }
        else {
            bootstrapResponseBean.setInformation("Client can not acknowledge by bootstrap server.");
        }

        return bootstrapResponseBean;
    }
}