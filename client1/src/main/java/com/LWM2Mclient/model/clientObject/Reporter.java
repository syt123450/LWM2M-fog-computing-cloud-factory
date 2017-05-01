package com.LWM2Mclient.model.clientObject;

import com.LWM2Mclient.model.entity.clientEntity.operation.JobBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.ReporterBean;
import com.LWM2Mclient.model.utils.MysqlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/22.
 */
public class Reporter implements Runnable {

    private Logger logger = Logger.getLogger(Reporter.class);
    private Gson gson = new GsonBuilder().create();
    private String receiver;

    private boolean force = false;
    private String environment;
    private int timeStep = 0;
    static private int MIN_STEP = 5;
    static private int MAX_STEP = 20;

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public void run() {
        try {

            environment = MysqlUtils.getEnvironment();
            sendReport();

            while (!Thread.currentThread().isInterrupted()) {

                String environmentNow = MysqlUtils.getEnvironment();

                if (force && timeStep >= MIN_STEP) {
                    environment = environmentNow;
                    sendReport();
                    force = false;
                } else {
                    if (!environmentNow.equals(environment)) {
                        if (timeStep >= MIN_STEP) {
                            environment = environmentNow;
                            sendReport();
                        } else {
                            force = true;
                        }
                    } else {
                        if (timeStep > MAX_STEP) {
                            sendReport();
                        }
                    }
                }

                timeStep++;
                environment = environmentNow;

                Thread.sleep(2000);
            }
        } catch (Exception e) {

        }
    }

    private void sendReport() throws Exception {

        ReporterBean reporterBean = new ReporterBean();
        reporterBean.setClientName("client1");
        reporterBean.setExecutorStatus(MysqlUtils.readResourceStatus("executor"));
        reporterBean.setWorkingStatus(environment);
        JobBean jobBean = MysqlUtils.getJobNow();
        reporterBean.setJobID(jobBean.getJobID());
        reporterBean.setLeftNum(jobBean.getMugNumber());

        String reportUrl = MysqlUtils.getOneServerInfo(receiver, "reporterUrl");
        String message = gson.toJson(reporterBean);

        HttpEntity httpEntity = new StringEntity(message);
        String responseContent = Request.Post(reportUrl).body(httpEntity).execute().returnContent().asString();
        logger.info("Response content is: " + responseContent);
        logger.info("Time step is: " + timeStep);
        timeStep = 0;
    }
}