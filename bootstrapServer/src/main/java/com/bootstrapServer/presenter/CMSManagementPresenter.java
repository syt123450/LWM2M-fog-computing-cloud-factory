package com.bootstrapServer.presenter;

import com.bootstrapServer.model.management.*;
import com.bootstrapServer.model.entity.CMSEntity.CMSAddRequestBean;
import com.bootstrapServer.model.entity.CMSEntity.CMSDeleteRequestBean;
import com.bootstrapServer.model.entity.serverEntity.CMSOperationResponseBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ss on 2017/3/3.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/CMS")
class CMSManagementPresenter {

    private Logger logger = Logger.getLogger(CMSBootstrapPresenter.class);
    private Gson gson = new GsonBuilder().create();


    @RequestMapping(value = "/serverManage/delete", method = RequestMethod.POST)
    private String deleteOneServer(@RequestBody String body) {

        CMSDeleteRequestBean cmsDeleteRequestBean = gson.fromJson(body, CMSDeleteRequestBean.class);
        Subtractor subtractor = new Subtractor();
        CMSOperationResponseBean cmsOperationResponseBean = subtractor.sub(cmsDeleteRequestBean);
        String response = gson.toJson(cmsOperationResponseBean);

        return response;
    }

    @RequestMapping(value = "/serverManage/add", method = RequestMethod.POST)
    private String addOneServer(@RequestBody String body) {

        CMSAddRequestBean cmsAddRequestBean = gson.fromJson(body, CMSAddRequestBean.class);
        Adder adder = new Adder();
        CMSOperationResponseBean cmsOperationResponseBean = adder.add(cmsAddRequestBean);
        String response = gson.toJson(cmsOperationResponseBean);

        return response;
    }
}