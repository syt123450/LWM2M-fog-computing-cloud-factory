package com.LWM2Mserver.presenter;

import com.LWM2Mserver.model.entity.serverEntity.ClientObjectBean;
import com.LWM2Mserver.model.serverObject.Discover;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by ss on 2017/4/23.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/discover")
class DiscoverPresenter {

    private Logger logger = Logger.getLogger(DiscoverPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/holder")
    public String discoverHolder() {

        logger.info("Discover WorkListHolder.");

        Discover discover = new Discover();
        ArrayList<ClientObjectBean> clientObjectBeans = discover.findListHolder();
        String response = gson.toJson(clientObjectBeans);

        return response;
    }
}