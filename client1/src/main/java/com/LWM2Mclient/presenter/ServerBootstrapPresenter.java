package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.entity.clientEntity.bootstrap.ServerInitiatedResponseBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

import com.LWM2Mclient.model.entity.serverEntity.BootstrapDataBean;
import com.LWM2Mclient.model.bootstrap.ServerInitiator;

/**
 * Created by ss on 2017/3/3.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/bootstrap")
class ServerBootstrapPresenter {

    private Logger logger = Logger.getLogger(ServerBootstrapPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/server", method = RequestMethod.POST)
    private String serverInitiated(@RequestBody String body) {

        BootstrapDataBean bootstrapDataBean = gson.fromJson(body, BootstrapDataBean.class);
        ServerInitiator serverInitiator = new ServerInitiator();
        ServerInitiatedResponseBean CMSBootstrapResultBean = serverInitiator.initiate(bootstrapDataBean);
        String response = gson.toJson(CMSBootstrapResultBean);

        return response;
    }
}