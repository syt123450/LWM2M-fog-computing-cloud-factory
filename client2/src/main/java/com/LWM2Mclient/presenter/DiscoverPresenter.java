package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.clientObject.Discover;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ss on 2017/4/22.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/discover")
class DiscoverPresenter {

    private Logger logger = Logger.getLogger(ExecutorPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/holder")
    public String discoverHolder() {

        Discover discover = new Discover();
        OperationMessageBean operationMessageBean = discover.find();
        String response = gson.toJson(operationMessageBean);

        return response;
    }
}