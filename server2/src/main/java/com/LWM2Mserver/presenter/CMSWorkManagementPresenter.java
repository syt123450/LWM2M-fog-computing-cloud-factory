package com.LWM2Mserver.presenter;

import com.LWM2Mserver.model.checker.WorkListChecker;
import com.LWM2Mserver.model.entity.CMSEntity.AddJobRequestBean;
import com.LWM2Mserver.model.entity.CMSEntity.CheckWorkListRequestBean;
import com.LWM2Mserver.model.entity.CMSEntity.DeleteJobRequestBean;
import com.LWM2Mserver.model.entity.clientEntity.OperationMessageBean;
import com.LWM2Mserver.model.entity.clientEntity.WorkListBean;
import com.LWM2Mserver.model.serverObject.Manager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/2.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/CMS/workManage")
class CMSWorkManagementPresenter {

    private Logger logger = Logger.getLogger(CMSWorkManagementPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private String addJob(@RequestBody String body) {

        AddJobRequestBean addJobRequestBean = gson.fromJson(body, AddJobRequestBean.class);
        Manager manager = new Manager();
        OperationMessageBean operationMessageBean = manager.add(addJobRequestBean);
        String response = gson.toJson(operationMessageBean);

        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteJob(@RequestBody String body) {

        DeleteJobRequestBean deleteJobRequestBean = gson.fromJson(body, DeleteJobRequestBean.class);
        Manager manager = new Manager();
        OperationMessageBean operationMessageBean = manager.delete(deleteJobRequestBean);
        String response = gson.toJson(operationMessageBean);

        return response;
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    private String checkWorkList(@RequestBody String body) {

        CheckWorkListRequestBean checkWorkListRequestBean = gson.fromJson(body, CheckWorkListRequestBean.class);
        WorkListChecker workListChecker = new WorkListChecker();
        WorkListBean workListBean = workListChecker.check(checkWorkListRequestBean);
        String response = gson.toJson(workListBean);

        return response;
    }
}
