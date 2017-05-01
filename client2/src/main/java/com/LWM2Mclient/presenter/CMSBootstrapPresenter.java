package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.bootstrap.Deleter;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.LWM2Mclient.model.entity.clientEntity.bootstrap.CMSBootstrapResultBean;
import com.LWM2Mclient.model.bootstrap.ClientInitiator;

/**
 * Created by ss on 2017/3/3.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/CMS/bootstrap")
class CMSBootstrapPresenter {

    private Logger logger = Logger.getLogger(CMSBootstrapPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/client")
    private String clientInitiated() {

        ClientInitiator clientInitiator = new ClientInitiator();
        CMSBootstrapResultBean CMSBootstrapResultBean = clientInitiator.initiated();
        String response = gson.toJson(CMSBootstrapResultBean);

        return response;
    }

    @RequestMapping("/delete")
    private String deleteBootstrap() {

        Deleter deleter = new Deleter();
        OperationMessageBean operationMessageBean = deleter.delete();
        String response = gson.toJson(operationMessageBean);

        return response;
    }
}