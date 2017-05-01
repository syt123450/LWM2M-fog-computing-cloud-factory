package com.LWM2Mclient.model.clientObject;

import com.LWM2Mclient.model.entity.clientEntity.operation.IdentityBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import com.LWM2Mclient.model.utils.MongodbUtils;
import com.LWM2Mclient.model.utils.MysqlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/26.
 */
public class ReportProvider {

    static private String CREATE_FAILED_MESSAGE = "Failed send request to server to create report.";
    private Logger logger = Logger.getLogger(ReportProvider.class);
    private Gson gson = new GsonBuilder().create();

    public OperationMessageBean create() {

        OperationMessageBean operationMessageBean = new OperationMessageBean();

        IdentityBean identityBean = new IdentityBean();
        identityBean.getIdentity();

        try {
            String createReportUrl = MysqlUtils.getCentralServerResource("createReportUrl");

            logger.info("Send create report request to server url: " + createReportUrl);

            String message = gson.toJson(identityBean);

            HttpEntity httpEntity = new StringEntity(message);
            String responseContent = Request.Post(createReportUrl).body(httpEntity).execute().returnContent().asString();
            operationMessageBean = gson.fromJson(responseContent, OperationMessageBean.class);

        } catch (Exception e) {
            operationMessageBean.setResultMessage(CREATE_FAILED_MESSAGE);
        }

        return operationMessageBean;
    }
}
