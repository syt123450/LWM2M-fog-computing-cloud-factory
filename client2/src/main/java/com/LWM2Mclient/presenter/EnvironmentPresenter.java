package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.clientObject.EnvironmentSensor;
import com.LWM2Mclient.model.entity.CMSEntity.ChangeEnvironmentBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ss on 2017/4/23.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/environment")
class EnvironmentPresenter {

    private Logger logger = Logger.getLogger(EnvironmentPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public String changeEnvironment(@RequestBody String body) {

        logger.info("Change environment.");

        ChangeEnvironmentBean changeEnvironmentBean = gson.fromJson(body, ChangeEnvironmentBean.class);

        EnvironmentSensor environmentSensor = new EnvironmentSensor();
        OperationMessageBean operationMessageBean = environmentSensor.change(changeEnvironmentBean);
        String response = gson.toJson(operationMessageBean);

        return response;
    }

    @RequestMapping("/check")
    public String checkEnvironment() {

        logger.info("Check environment.");

        EnvironmentSensor environmentSensor = new EnvironmentSensor();
        OperationMessageBean operationMessageBean = environmentSensor.check();
        String response = gson.toJson(operationMessageBean);

        return response;
    }
}