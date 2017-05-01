package com.LWM2Mclient.model.clientObject;

import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import com.LWM2Mclient.model.entity.serverEntity.JobAddBean;
import com.LWM2Mclient.model.entity.serverEntity.JobDeleteBean;
import com.LWM2Mclient.model.utils.MysqlUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by ss on 2017/4/2.
 */

public class WorkListHolder {

    static private String SUCCESS_SWITCH_MESSAGE = "The workListHolder is successfully turned %s.";
    static private String FAILED_SWITCH_MESSAGE = "Failed to switch the workListHolder.";
    static private String SUCCESS_DELETE_MESSAGE = "Successfully delete the job(id is %d).";
    static private String FAILED_DELETE_MESSAGE = "Failed to delete the job.";
    static private String SUCCESS_ADD_MESSAGE = "Successfully add the job.";
    static private String FAILED_ADD_MESSAGE = "Failed to add the job.";

    private Logger logger = Logger.getLogger(WorkListHolder.class);

    public OperationMessageBean switchHolder(String operationName) {

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        String operateResult = MysqlUtils.updateResourceStatus("workListHolder", operationName);

        logger.info("OperateResult is " + operateResult);

        if(operateResult.equals("success")) {
            String formatMessage = String.format(SUCCESS_SWITCH_MESSAGE, operationName);
            operationMessageBean.setResultMessage(formatMessage);
        }
        else {
            operationMessageBean.setResultMessage(FAILED_SWITCH_MESSAGE);
        }

        return operationMessageBean;
    }

    public OperationMessageBean add(JobAddBean jobAddBean) {

        ArrayList jobArgs = new ArrayList();
        jobArgs.add(jobAddBean.getMugColor());
        jobArgs.add(jobAddBean.getMugSize());
        jobArgs.add(jobAddBean.getMugPattern());
        jobArgs.add(jobAddBean.getMugNumber());

        String operateResult =  MysqlUtils.addJob(jobArgs);

        logger.info("OperateResult is " + operateResult);

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        if(operateResult.equals("success")) {
            operationMessageBean.setResultMessage(SUCCESS_ADD_MESSAGE);
        }
        else {
            operationMessageBean.setResultMessage(FAILED_ADD_MESSAGE);
        }

        return operationMessageBean;
    }

    public OperationMessageBean delete(JobDeleteBean jobDeleteBean) {

        int jobID = jobDeleteBean.getJobID();

        String operateResult = MysqlUtils.deleteJob(jobID);

        logger.info("OperateResult is " + operateResult);

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        if(operateResult.equals("success")) {
            String formatMessage = String.format(SUCCESS_DELETE_MESSAGE, jobID);
            logger.info(formatMessage);
            operationMessageBean.setResultMessage(formatMessage);
        }
        else {
            operationMessageBean.setResultMessage(FAILED_DELETE_MESSAGE);
        }

        return operationMessageBean;
    }
}