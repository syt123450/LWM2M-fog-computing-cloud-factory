package com.bootstrapServer.presenter;

import com.bootstrapServer.model.checker.*;
import com.bootstrapServer.model.entity.serverEntity.ClientInfoBean;
import com.bootstrapServer.model.entity.serverEntity.ServerInfoBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/check")
class CMSCheckingPresenter {

    private Logger logger = Logger.getLogger(CMSBootstrapPresenter.class);
    private Gson gson = new GsonBuilder().create();


    @RequestMapping("/bootstrap")
    private String checkBootstrapInfo() {

        BootstrapChecker bootstrapChecker = new BootstrapChecker();
        ArrayList<ServerInfoBean> serverInfoBeans = bootstrapChecker.check();
        String response = gson.toJson(serverInfoBeans);

        return response;
    }

    @RequestMapping("/client")
    private String checkClientInfo() {

        ClientChecker clientChecker = new ClientChecker();
        ArrayList<ClientInfoBean> clientInfoBeans = clientChecker.check();
        String response = gson.toJson(clientInfoBeans);

        return response;
    }
}