package com.LWM2Mserver.model.serverObject;

import com.LWM2Mserver.model.entity.CMSEntity.SwitchResourceRequestBean;
import com.LWM2Mserver.model.entity.clientEntity.OperationMessageBean;
import com.LWM2Mserver.model.entity.clientEntity.ReporterRequestBean;
import com.LWM2Mserver.model.entity.serverEntity.PaymentCheckingBean;
import com.LWM2Mserver.model.entity.serverEntity.PaymentCheckingResponseBean;
import com.LWM2Mserver.model.entity.serverEntity.ReceiverResponseBean;
import com.LWM2Mserver.model.utils.MongodbUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by ss on 2017/4/23.
 */
public class Receiver {

    static private String SUCCESS_PERSIST_MESSAGE = "Successfully persist the report.";
    static private String FAILED_PERSIST_MESSAGE = "Some error happens in checking";
    static private String CHECKING_URL = "http://localhost:8000/payment/check";
    private Logger logger = Logger.getLogger(Receiver.class);
    private Gson gson = new GsonBuilder().create();

    public ReceiverResponseBean persist(ReporterRequestBean reporterRequestBean) {

        String clientName = reporterRequestBean.getClientName();

        ReceiverResponseBean receiverResponseBean = new ReceiverResponseBean();

        try {

            PaymentCheckingResponseBean paymentCheckingResponseBean = checkClient(clientName);

            logger.info("The checking result is: " + paymentCheckingResponseBean.isCheckResult());

            if (paymentCheckingResponseBean.isCheckResult()) {
                HashMap<String, String> report = new HashMap<>();
                report.put("clientName", clientName);
                report.put("executorStatus", reporterRequestBean.getExecutorStatus());
                report.put("workingStatus", reporterRequestBean.getWorkingStatus());
                report.put("jobID", String.valueOf(reporterRequestBean.getJobID()));
                report.put("leftNum", String.valueOf(reporterRequestBean.getLeftNum()));
                report.put("timestamp", String.valueOf(System.currentTimeMillis()));
                MongodbUtils.persistReport(report);

                logger.info("Executor status is: " + reporterRequestBean.getExecutorStatus());
                logger.info("Working status is: " + reporterRequestBean.getWorkingStatus());

                if (reporterRequestBean.getExecutorStatus().equals("ON") && reporterRequestBean.getWorkingStatus().equals("Bad")) {
                    handleException(clientName);
                }

                receiverResponseBean.setResponseMessage(SUCCESS_PERSIST_MESSAGE);
            } else {
                receiverResponseBean.setResponseMessage(paymentCheckingResponseBean.getCheckResultMessage());
            }
        } catch (Exception e) {
            receiverResponseBean.setResponseMessage(FAILED_PERSIST_MESSAGE);
        }

        return receiverResponseBean;
    }

    private PaymentCheckingResponseBean checkClient(String clientName) throws Exception {

        PaymentCheckingBean paymentCheckingBean = new PaymentCheckingBean();
        paymentCheckingBean.setClientName(clientName);

        String message = gson.toJson(paymentCheckingBean);

        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(CHECKING_URL).body(httpEntity).execute().returnContent().asString();
        PaymentCheckingResponseBean paymentCheckingResponseBean = gson.fromJson(responseContent, PaymentCheckingResponseBean.class);

        logger.info("Payment checking result is: " + paymentCheckingResponseBean);

        return paymentCheckingResponseBean;
    }

    private void handleException(String clientName) throws  Exception {
        SwitchResourceRequestBean switchResourceRequestBean = new SwitchResourceRequestBean();
        switchResourceRequestBean.setClientName(clientName);
        switchResourceRequestBean.setResourceName("executor");
        switchResourceRequestBean.setSwitchOperation("turn_off");
        Switcher switcher = new Switcher();
        OperationMessageBean operationMessageBean = switcher.switchResource(switchResourceRequestBean);
        logger.info("Operation result message is: " + operationMessageBean);
    }
}
