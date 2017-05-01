package com.bootstrapServer.presenter;

import com.bootstrapServer.model.bootstrap.ServerInitiator;
import com.bootstrapServer.model.entity.serverEntity.CMSBootstrapResultBean;
import com.bootstrapServer.model.entity.CMSEntity.ServerInitiateRequestBean;
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
class CMSBootstrapPresenter {

    private Logger logger = Logger.getLogger(CMSBootstrapPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/serverInitiated", method = RequestMethod.POST)
    private String initiateOne(@RequestBody String body) throws Exception{

        ServerInitiateRequestBean serverInitiateRequestBean = gson.fromJson(body, ServerInitiateRequestBean.class);
        ServerInitiator serverInitiator = new ServerInitiator();
        CMSBootstrapResultBean cmsBootstrapResultBean = serverInitiator.initiate(serverInitiateRequestBean);
        String response = gson.toJson(cmsBootstrapResultBean);

        return response;
    }
}