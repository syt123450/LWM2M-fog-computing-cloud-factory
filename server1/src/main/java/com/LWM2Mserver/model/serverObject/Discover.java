package com.LWM2Mserver.model.serverObject;

import com.LWM2Mserver.model.entity.clientEntity.OperationMessageBean;
import com.LWM2Mserver.model.entity.serverEntity.ClientObjectBean;
import com.LWM2Mserver.model.utils.MongodbUtils;
import com.LWM2Mserver.model.utils.PropertiesUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.fluent.Request;

import java.util.ArrayList;

/**
 * Created by ss on 2017/4/23.
 */
public class Discover {

    static private String CONNECT_SUCCESS_MESSAGE = "Successfully connect to client.";
    static private String CONNECT_FAILED_MESSAGE = "Failed to connect.";

    private Gson gson = new GsonBuilder().create();

    public ArrayList<ClientObjectBean> findListHolder() {

        ArrayList<ClientObjectBean> clientObjectBeans = new ArrayList<>();

        ArrayList<String> clientNameList = MongodbUtils.getAllClientName();

        for (int i = 0; i < clientNameList.size(); i++) {
            String clientName = clientNameList.get(i);

            ClientObjectBean clientObjectBean = new ClientObjectBean();
            clientObjectBean.setServerName(PropertiesUtils.getProperty("name"));
            clientObjectBean.setClientName(clientName);
            clientObjectBean.setClientObjectName("WorkListHolder");
            String discoverUrl = MongodbUtils.getResourceOperationUrl(clientName, "discover", "find_workListHolder");

            try {
                String responseContent = Request.Get(discoverUrl).execute().returnContent().asString();
                OperationMessageBean operationMessageBean = gson.fromJson(responseContent, OperationMessageBean.class);
                String status = operationMessageBean.getResultMessage();
                clientObjectBean.setClientObjectStatus(status);

                clientObjectBean.setConnectStatus(CONNECT_SUCCESS_MESSAGE);
            } catch (Exception e) {
                clientObjectBean.setConnectStatus(CONNECT_FAILED_MESSAGE);
            }

            clientObjectBeans.add(clientObjectBean);
        }

        return clientObjectBeans;
    }
}