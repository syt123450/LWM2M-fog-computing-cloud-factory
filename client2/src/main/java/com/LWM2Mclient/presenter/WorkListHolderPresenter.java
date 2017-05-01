package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.checker.ResourceChecker;
import com.LWM2Mclient.model.entity.clientEntity.operation.WorkListBean;
import com.LWM2Mclient.model.entity.serverEntity.JobAddBean;
import com.LWM2Mclient.model.entity.serverEntity.JobDeleteBean;
import com.LWM2Mclient.model.clientObject.WorkListHolder;
import com.LWM2Mclient.model.entity.clientEntity.operation.ResourceAttributeBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import com.LWM2Mclient.model.checker.WorkListChecker;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ss on 2017/4/2.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/workListHolder")
class WorkListHolderPresenter {

    private Logger logger = Logger.getLogger(WorkListHolderPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/on")
    public String turnOnHolder() {

        logger.info("Turn on workListHolder.");

        WorkListHolder executor = new WorkListHolder();
        OperationMessageBean resultMessage = executor.switchHolder("ON");
        String response = gson.toJson(resultMessage);

        return response;
    }

    @RequestMapping("/off")
    public String turnOffHolder() {

        logger.info("Turn off workListHolder.");

        WorkListHolder executor = new WorkListHolder();
        OperationMessageBean resultMessage = executor.switchHolder("OFF");
        String response = gson.toJson(resultMessage);

        return response;
    }

    @RequestMapping("/readAttribute")
    public String readHolderValue() {

        logger.info("Check Status of executor.");

        ResourceChecker resourceChecker = new ResourceChecker();
        ResourceAttributeBean resourceAttributeBean = resourceChecker.check("workListHolder");
        String response = gson.toJson(resourceAttributeBean);

        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addJob(@RequestBody String body) {

        logger.info("Add job");

        JobAddBean jobAddBean = gson.fromJson(body, JobAddBean.class);
        WorkListHolder workListHolder = new WorkListHolder();
        OperationMessageBean operationMessageBean = workListHolder.add(jobAddBean);
        String response = gson.toJson(operationMessageBean);

        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteJob(@RequestBody String body) {

        logger.info("Delete job");

        JobDeleteBean jobDeleteBean = gson.fromJson(body, JobDeleteBean.class);
        WorkListHolder workListHolder = new WorkListHolder();
        OperationMessageBean operationMessageBean = workListHolder.delete(jobDeleteBean);
        String response = gson.toJson(operationMessageBean);

        return response;
    }

    @RequestMapping("/readList")
    public String readJob() {

        logger.info("Check workList.");

        WorkListChecker workListChecker = new WorkListChecker();
        WorkListBean workListBean = workListChecker.check();
        String response = gson.toJson(workListBean);

        return response;
    }
}