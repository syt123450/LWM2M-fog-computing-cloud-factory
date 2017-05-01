package com.LWM2Mserver.model.serverObject;

import com.LWM2Mserver.model.entity.CMSEntity.AddJobRequestBean;
import com.LWM2Mserver.model.entity.CMSEntity.DeleteJobRequestBean;
import com.LWM2Mserver.model.entity.clientEntity.OperationMessageBean;
import com.LWM2Mserver.model.entity.serverEntity.JobAddBean;
import com.LWM2Mserver.model.entity.serverEntity.JobDeleteBean;
import com.LWM2Mserver.model.utils.MongodbUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/2.
 */
public class Manager {

    private static String RESOURCE = "workListHolder";
    private static String ADD_OPERATION = "add_job";
    private static String DELETE_OPERATION = "delete_job";
    private static String DELETE_ERROR_MESSAGE = "Some error happen in delete the job.";
    private static String ADD_ERROR_MESSAGE = "Some error happen in add new job.";

    private Logger logger = Logger.getLogger(Manager.class);
    private Gson gson = new GsonBuilder().create();

    public OperationMessageBean add(AddJobRequestBean addJobRequestBean) {

        String clientName = addJobRequestBean.getClientName();
        String addUrl = MongodbUtils.getResourceOperationUrl(clientName, RESOURCE, ADD_OPERATION);

        logger.info("Add job url is: " + addUrl);

        JobAddBean jobAddBean = new JobAddBean();
        jobAddBean.setMugColor(addJobRequestBean.getMugColor());
        jobAddBean.setMugNumber(addJobRequestBean.getMugNumber());
        jobAddBean.setMugPattern(addJobRequestBean.getMugPattern());
        jobAddBean.setMugSize(addJobRequestBean.getMugSize());

        String message = gson.toJson(jobAddBean);

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        try {
            HttpEntity httpEntity = new StringEntity(message);
            String responseContent = Request.Post(addUrl).body(httpEntity).execute().returnContent().asString();
            operationMessageBean = gson.fromJson(responseContent, OperationMessageBean.class);
        }
        catch (Exception e) {
            operationMessageBean.setResultMessage(ADD_ERROR_MESSAGE);
        }

        return operationMessageBean;
    }

    public OperationMessageBean delete(DeleteJobRequestBean deleteJobRequestBean) {

        String clientName = deleteJobRequestBean.getClientName();
        String deleteUrl = MongodbUtils.getResourceOperationUrl(clientName, RESOURCE, DELETE_OPERATION);

        logger.info("Delete job url is: " + deleteUrl);

        JobDeleteBean jobDeleteBean = new JobDeleteBean();
        jobDeleteBean.setJobID(deleteJobRequestBean.getJobID());

        String message = gson.toJson(jobDeleteBean);

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        try {
            HttpEntity httpEntity = new StringEntity(message);
            String responseContent = Request.Post(deleteUrl).body(httpEntity).execute().returnContent().asString();
            operationMessageBean = gson.fromJson(responseContent, OperationMessageBean.class);
        }
        catch (Exception e) {
            operationMessageBean.setResultMessage(DELETE_ERROR_MESSAGE);
        }

        return operationMessageBean;
    }
}
