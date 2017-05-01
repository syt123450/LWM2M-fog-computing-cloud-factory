package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.clientObject.Reporter;
import com.LWM2Mclient.model.entity.clientEntity.operation.ObservationRequestBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

/**
 * Created by ss on 2017/4/22.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/reporter")
class ReportPresenter {

    static private String SUCCESS_OBSERVE_MESSAGE = "Successfully observe the client.";
    static private String FAILED_OBSERVE_MESSAGE = "Server has already observe the client.";
    static private String SUCCESS_CANCEL_MESSAGE = "Successfully cancel the observation.";
    static private String FAILED_CANCEL_MESSAGE = "Server must observe the client before cancel observation.";
    private Logger logger = Logger.getLogger(ExecutorPresenter.class);
    private Gson gson = new GsonBuilder().create();

    HashMap<String, Thread> observationMap = new HashMap<>();

    @RequestMapping(value = "/observe", method = RequestMethod.POST)
    public String observe(@RequestBody String body) {

        ObservationRequestBean observationRequestBean = gson.fromJson(body, ObservationRequestBean.class);
        String serverName = observationRequestBean.getServerName();

        logger.info("Observe request from: " + serverName);

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        if (observationMap.get(serverName) == null) {
            Reporter reporter = new Reporter();
            reporter.setReceiver(serverName);
            Thread thread = new Thread(reporter);
            thread.start();
            observationMap.put(serverName, thread);
            operationMessageBean.setResultMessage(SUCCESS_OBSERVE_MESSAGE);
        }
        else {
            operationMessageBean.setResultMessage(FAILED_OBSERVE_MESSAGE);
        }

        String response = gson.toJson(operationMessageBean);

        return response;
    }

    @RequestMapping("/cancel")
    public String cancelObservation(@RequestBody String body) {

        ObservationRequestBean observationRequestBean = gson.fromJson(body, ObservationRequestBean.class);
        String serverName = observationRequestBean.getServerName();

        logger.info("Cancel Observation from: " + serverName);

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        if (observationMap.get(serverName) != null) {
            Thread thread = observationMap.get(serverName);
            thread.interrupt();
            observationMap.remove(serverName);
            operationMessageBean.setResultMessage(SUCCESS_CANCEL_MESSAGE);
        }
        else {
            operationMessageBean.setResultMessage(FAILED_CANCEL_MESSAGE);
        }

        String response = gson.toJson(operationMessageBean);

        return response;
    }
}
