package com.LWM2Mserver.model.serverObject;

import com.LWM2Mserver.model.entity.CMSEntity.ReportRequestBodyBean;
import com.LWM2Mserver.model.entity.serverEntity.ObservationRequestBean;
import com.LWM2Mserver.model.entity.serverEntity.ReporterResponseBean;
import com.LWM2Mserver.model.utils.MongodbUtils;
import com.LWM2Mserver.model.utils.PropertiesUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;

/**
 * Created by ss on 2017/4/24.
 */
public class ReportRequester {

    static private String FAILED_MESSAGE = "Failed to connect to client.";
    private Gson gson = new GsonBuilder().create();

    public ReporterResponseBean observe(ReportRequestBodyBean reportRequestBodyBean) {

        String clientName = reportRequestBodyBean.getClientName();
        String requestUrl = MongodbUtils.getResourceOperationUrl(clientName, "reporter", "observe");
        ReporterResponseBean reporterResponseBean = sendRequest(requestUrl);

        return reporterResponseBean;
    }

    public ReporterResponseBean cancel(ReportRequestBodyBean reportRequestBodyBean) {

        String clientName = reportRequestBodyBean.getClientName();
        String requestUrl = MongodbUtils.getResourceOperationUrl(clientName, "reporter", "cancel");
        ReporterResponseBean reporterResponseBean = sendRequest(requestUrl);

        return reporterResponseBean;
    }

    private ReporterResponseBean sendRequest(String requestUrl) {

        ReporterResponseBean reporterResponseBean = new ReporterResponseBean();

        try {
            ObservationRequestBean observationRequestBean = new ObservationRequestBean();
            observationRequestBean.setServerName(PropertiesUtils.getProperty("name"));
            String message = gson.toJson(observationRequestBean);
            HttpEntity httpEntity = new StringEntity(message);
            String responseContent = Request.Post(requestUrl).body(httpEntity).execute().returnContent().asString();
            reporterResponseBean = gson.fromJson(responseContent, ReporterResponseBean.class);
        } catch (Exception e) {
            reporterResponseBean.setResultMessage(FAILED_MESSAGE);
        }

        return reporterResponseBean;
    }
}
