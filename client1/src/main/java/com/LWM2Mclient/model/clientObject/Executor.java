package com.LWM2Mclient.model.clientObject;

import com.LWM2Mclient.model.entity.clientEntity.operation.ExecutorBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import com.LWM2Mclient.model.utils.MysqlUtils;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/2.
 */


public class Executor {

    static private String SUCCESS_SWITCH_MESSAGE = "The executor is successfully turned %s.";
    static private String FAILED_SWITCH_MESSAGE = "Failed to switch the executor.";
    static private int REPORT_INTERVAL = 2000;

    private Logger logger = Logger.getLogger(Executor.class);

    public ExecutorBean on() {

        ExecutorBean executorBean = switchExecutor("ON");
        if (executorBean.isOperation()) {
            executorBean.setThread(startExecutorThread());
        }

        return executorBean;
    }

    public ExecutorBean off() {

        ExecutorBean executorBean = switchExecutor("OFF");

        return executorBean;
    }

    private ExecutorBean switchExecutor(String operationName) {

        ExecutorBean executorBean = new ExecutorBean();
        OperationMessageBean operationMessageBean = new OperationMessageBean();
        String operateResult = MysqlUtils.updateResourceStatus("executor", operationName);

        logger.info("OperateResult is " + operateResult);

        if (operateResult.equals("success")) {
            String formatMessage = String.format(SUCCESS_SWITCH_MESSAGE, operationName);
            operationMessageBean.setResultMessage(formatMessage);
            executorBean.setOperationMessageBean(operationMessageBean);
            executorBean.setOperation(true);
        } else {
            operationMessageBean.setResultMessage(FAILED_SWITCH_MESSAGE);
            executorBean.setOperationMessageBean(operationMessageBean);
            executorBean.setOperation(false);
        }

        return executorBean;
    }

    private Thread startExecutorThread() {

        ExecutorTask executorTask = new ExecutorTask();
        Thread thread = new Thread(executorTask);
        thread.start();

        return thread;
    }

    class ExecutorTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    MysqlUtils.deleteJobItem();
                    Thread.sleep(REPORT_INTERVAL);
                }
            } catch (Exception e) {

            }
        }
    }
}