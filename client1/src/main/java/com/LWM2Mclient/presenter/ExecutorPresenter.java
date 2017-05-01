package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.entity.clientEntity.operation.ExecutorBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.LWM2Mclient.model.clientObject.Executor;
import com.LWM2Mclient.model.checker.ResourceChecker;
import com.LWM2Mclient.model.entity.clientEntity.operation.ResourceAttributeBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;

/**
 * Created by ss on 2017/4/2.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/executor")
class ExecutorPresenter {

    private Logger logger = Logger.getLogger(ExecutorPresenter.class);
    private Gson gson = new GsonBuilder().create();
    private Thread thread;

    @RequestMapping("/on")
    public String turnOnExecutor() {

        logger.info("Turn on executor.");

        Executor executor = new Executor();

        ExecutorBean executorBean = executor.on();
        if(executorBean.isOperation()) {
            thread = executorBean.getThread();
        }
        OperationMessageBean operationMessageBean = executorBean.getOperationMessageBean();
        String response = gson.toJson(operationMessageBean);

        return response;
    }

    @RequestMapping("/off")
    public String turnOffExecutor() {

        logger.info("Turn off executor.");

        Executor executor = new Executor();
        ExecutorBean executorBean = executor.off();
        logger.info(executorBean.isOperation());
        if(executorBean.isOperation()) {
            thread.interrupt();
        }
        OperationMessageBean operationMessageBean = executorBean.getOperationMessageBean();
        String response = gson.toJson(operationMessageBean);

        return response;
    }

    @RequestMapping("/readAttribute")
    public String readExecutorValue() {

        logger.info("Check Status of executor.");

        ResourceChecker resourceChecker = new ResourceChecker();
        ResourceAttributeBean resourceAttributeBean = resourceChecker.check("executor");
        String response = gson.toJson(resourceAttributeBean);

        return response;
    }
}