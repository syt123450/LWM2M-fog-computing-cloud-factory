package com.LWM2Mserver.model.serverObject;

import com.LWM2Mserver.model.entity.CMSEntity.SwitchResourceRequestBean;
import com.LWM2Mserver.model.entity.clientEntity.OperationMessageBean;
import com.LWM2Mserver.model.utils.MongodbUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/2.
 */
public class Switcher {

    private static String ERROR_MESSAGE = "Some error happen in switch operation.";
    private Logger logger = Logger.getLogger(Switcher.class);
    private Gson gson = new GsonBuilder().create();

    public OperationMessageBean switchResource(SwitchResourceRequestBean switchResourceRequestBean) {

        String clientName = switchResourceRequestBean.getClientName();
        String resourceName = switchResourceRequestBean.getResourceName();
        String operation = switchResourceRequestBean.getSwitchOperation();
        String switchUrl = MongodbUtils.getResourceOperationUrl(clientName, resourceName, operation);

        logger.info("Send request to switch operation url: " + switchUrl);

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        try {
            String responseContent = Request.Get(switchUrl).execute().returnContent().asString();
            operationMessageBean = gson.fromJson(responseContent, OperationMessageBean.class);
        }
        catch (Exception e) {
            operationMessageBean.setResultMessage(ERROR_MESSAGE);
        }

        logger.info("The switch operation result is: " + operationMessageBean);

        return operationMessageBean;
    }
}
