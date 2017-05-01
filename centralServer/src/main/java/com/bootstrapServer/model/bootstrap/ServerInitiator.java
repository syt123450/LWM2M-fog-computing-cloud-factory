package com.bootstrapServer.model.bootstrap;

import com.bootstrapServer.model.entity.serverEntity.*;
import com.bootstrapServer.model.entity.CMSEntity.ServerInitiateRequestBean;
import com.bootstrapServer.model.entity.clientEntity.ServerInitiatedResponseBean;
import com.bootstrapServer.model.utils.MongodbUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.fluent.Request;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

public class ServerInitiator {

    static private String FailedMessage = "Failed to boot the client";
    private Gson gson = new GsonBuilder().create();

    public CMSBootstrapResultBean initiate(ServerInitiateRequestBean serverInitiateRequestBean) throws Exception {

        String clientName = serverInitiateRequestBean.getClientName();
        String serverName = serverInitiateRequestBean.getServerName();

        if (clientName.equals("") && serverName.equals("")) {

            return bootstrapAllClientWithAllServer();
        } else if (!clientName.equals("") && serverName.equals("")) {

            return bootstrapOneClientWithAllServer(clientName);
        } else if (clientName.equals("") && !serverName.equals("")) {

            return bootstrapAllClientWithOneServer(serverName);
        } else {

            return bootstrapOneClientWithOneServer(clientName, serverName);
        }
    }

    private CMSBootstrapResultBean bootstrapAllClientWithAllServer() throws Exception {

        ArrayList<ClientInfoBean> clientInfoBeans = MongodbUtils.getAllClientInfo();
        ArrayList<ServerInfoBean> serverInfoBeans = MongodbUtils.getAllBootstrapData();
        CentralServerInfoBean centralServerInfoBean = MongodbUtils.getCentralServerInfo();
        BootstrapResponseBean bootstrapResponseBean = new BootstrapResponseBean();
        generateRequestBody(bootstrapResponseBean, true, serverInfoBeans, "All", null, true, centralServerInfoBean);

        ArrayList<ServerInitiatedResponseBean> resultList = new ArrayList<>();
        for (int i = 0; i < clientInfoBeans.size(); i++) {
            try {
                ServerInitiatedResponseBean serverInitiatedResponseBean =
                        sendInitiatedRequest(clientInfoBeans.get(i).getBootstrapUrl(), bootstrapResponseBean);
                resultList.add(serverInitiatedResponseBean);
            } catch (Exception e) {
                ServerInitiatedResponseBean serverInitiatedResponseBean = new ServerInitiatedResponseBean();
                serverInitiatedResponseBean.setClientName(clientInfoBeans.get(i).getClientName());
                serverInitiatedResponseBean.setBootResult(FailedMessage);
                resultList.add(serverInitiatedResponseBean);
            }
        }

        CMSBootstrapResultBean cmsBootstrapResultBean = new CMSBootstrapResultBean();
        cmsBootstrapResultBean.setResultList(resultList);

        return cmsBootstrapResultBean;
    }

    private CMSBootstrapResultBean bootstrapAllClientWithOneServer(String serverName) throws Exception {

        ArrayList<ClientInfoBean> clientInfoBeans = MongodbUtils.getAllClientInfo();
        ServerInfoBean serverInfoBean = MongodbUtils.getBootstrapData(serverName);
        ArrayList<ServerInfoBean> serverInfoBeans = new ArrayList<>();
        serverInfoBeans.add(serverInfoBean);
        ArrayList<String> serverNames = new ArrayList<>();
        serverNames.add(serverInfoBean.getServerName());
        BootstrapResponseBean bootstrapResponseBean = new BootstrapResponseBean();
        generateRequestBody(bootstrapResponseBean, true, serverInfoBeans, "One", serverNames, false, null);

        ArrayList<ServerInitiatedResponseBean> resultList = new ArrayList<>();
        for (int i = 0; i < clientInfoBeans.size(); i++) {
            try {
                ServerInitiatedResponseBean serverInitiatedResponseBean =
                        sendInitiatedRequest(clientInfoBeans.get(i).getBootstrapUrl(), bootstrapResponseBean);
                resultList.add(serverInitiatedResponseBean);
            } catch (Exception e) {
                ServerInitiatedResponseBean serverInitiatedResponseBean = new ServerInitiatedResponseBean();
                serverInitiatedResponseBean.setClientName(clientInfoBeans.get(i).getClientName());
                serverInitiatedResponseBean.setBootResult(FailedMessage);
                resultList.add(serverInitiatedResponseBean);
            }
        }

        CMSBootstrapResultBean cmsBootstrapResultBean = new CMSBootstrapResultBean();
        cmsBootstrapResultBean.setResultList(resultList);

        return cmsBootstrapResultBean;
    }

    private CMSBootstrapResultBean bootstrapOneClientWithAllServer(String clientName) throws Exception {

        ClientInfoBean clientInfoBean = MongodbUtils.getOneClientInfo(clientName);
        ArrayList<ServerInfoBean> serverInfoBeans = MongodbUtils.getAllBootstrapData();
        CentralServerInfoBean centralServerInfoBean = MongodbUtils.getCentralServerInfo();
        BootstrapResponseBean bootstrapResponseBean = new BootstrapResponseBean();
        generateRequestBody(bootstrapResponseBean, true, serverInfoBeans, "All", null, true, centralServerInfoBean);

        ArrayList<ServerInitiatedResponseBean> resultList = new ArrayList<>();
        try {
            ServerInitiatedResponseBean serverInitiatedResponseBean =
                    sendInitiatedRequest(clientInfoBean.getBootstrapUrl(), bootstrapResponseBean);
            resultList.add(serverInitiatedResponseBean);
        } catch (Exception e) {
            ServerInitiatedResponseBean serverInitiatedResponseBean = new ServerInitiatedResponseBean();
            serverInitiatedResponseBean.setClientName(clientName);
            serverInitiatedResponseBean.setBootResult(FailedMessage);
            resultList.add(serverInitiatedResponseBean);
        }

        CMSBootstrapResultBean cmsBootstrapResultBean = new CMSBootstrapResultBean();
        cmsBootstrapResultBean.setResultList(resultList);

        return cmsBootstrapResultBean;
    }

    private CMSBootstrapResultBean bootstrapOneClientWithOneServer(String clientName, String serverName) {

        ClientInfoBean clientInfoBean = MongodbUtils.getOneClientInfo(clientName);
        ServerInfoBean serverInfoBean = MongodbUtils.getBootstrapData(serverName);
        ArrayList<ServerInfoBean> serverInfoBeans = new ArrayList<>();
        serverInfoBeans.add(serverInfoBean);
        ArrayList<String> serverNames = new ArrayList<>();
        serverNames.add(serverInfoBean.getServerName());
        BootstrapResponseBean bootstrapResponseBean = new BootstrapResponseBean();
        generateRequestBody(bootstrapResponseBean, true, serverInfoBeans, "One", serverNames, false, null);

        ArrayList<ServerInitiatedResponseBean> resultList = new ArrayList<>();
        try {
            ServerInitiatedResponseBean serverInitiatedResponseBean =
                    sendInitiatedRequest(clientInfoBean.getBootstrapUrl(), bootstrapResponseBean);
            resultList.add(serverInitiatedResponseBean);
        } catch (Exception e) {
            ServerInitiatedResponseBean serverInitiatedResponseBean = new ServerInitiatedResponseBean();
            serverInitiatedResponseBean.setClientName(clientName);
            serverInitiatedResponseBean.setBootResult(FailedMessage);
            resultList.add(serverInitiatedResponseBean);
        }

        CMSBootstrapResultBean cmsBootstrapResultBean = new CMSBootstrapResultBean();
        cmsBootstrapResultBean.setResultList(resultList);

        return cmsBootstrapResultBean;
    }

    private void generateRequestBody(BootstrapResponseBean bootstrapResponseBean,
                                     boolean write,
                                     ArrayList<ServerInfoBean> writeList,
                                     String delete,
                                     ArrayList<String> deleteList,
                                     boolean centralServer,
                                     CentralServerInfoBean centralServerInfoBean
                                     ) {

        bootstrapResponseBean.setWrite(write);
        bootstrapResponseBean.setWriteList(writeList);
        bootstrapResponseBean.setDelete(delete);
        bootstrapResponseBean.setDeleteList(deleteList);
        bootstrapResponseBean.setCentralServer(centralServer);
        bootstrapResponseBean.setCentralServerInfoBean(centralServerInfoBean);

    }

    private ServerInitiatedResponseBean sendInitiatedRequest(String bootstrapUrl,
                                                             BootstrapResponseBean bootstrapResponseBean) throws Exception {

        String message = gson.toJson(bootstrapResponseBean);
        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(bootstrapUrl).body(httpEntity).execute().returnContent().asString();
        ServerInitiatedResponseBean serverInitiatedResponseBean = gson.fromJson(responseContent, ServerInitiatedResponseBean.class);

        return serverInitiatedResponseBean;
    }
}