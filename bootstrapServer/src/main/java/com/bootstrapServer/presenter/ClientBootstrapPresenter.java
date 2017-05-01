package com.bootstrapServer.presenter;

import com.bootstrapServer.model.bootstrap.ClientInitiator;
import com.bootstrapServer.model.entity.serverEntity.BootstrapResponseBean;
import com.bootstrapServer.model.entity.clientEntity.UsrBootstrapRequestBean;
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
@RequestMapping("/bootstrap")
class ClientBootstrapPresenter {

    private Logger logger = Logger.getLogger(ClientBootstrapPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/clientInitiated", method = RequestMethod.POST)
    private String clientInitiated(@RequestBody String body) {

        UsrBootstrapRequestBean usrBootstrapRequestBean = gson.fromJson(body, UsrBootstrapRequestBean.class);
        ClientInitiator clientInitiator = new ClientInitiator();
        BootstrapResponseBean bootstrapResponseBean = clientInitiator.initiate(usrBootstrapRequestBean);
        String reponse = gson.toJson(bootstrapResponseBean);

        return reponse;
    }
}