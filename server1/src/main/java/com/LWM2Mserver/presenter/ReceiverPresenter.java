package com.LWM2Mserver.presenter;

import com.LWM2Mserver.model.entity.clientEntity.ReporterRequestBean;
import com.LWM2Mserver.model.entity.serverEntity.ReceiverResponseBean;
import com.LWM2Mserver.model.serverObject.Receiver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/23.
 */

@EnableAutoConfiguration
@RestController

class ReceiverPresenter {

    private Logger logger = Logger.getLogger(ReceiverPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/receiver")
    private String receiver(@RequestBody String body) {

        logger.info("Receive reporter from client.");

        ReporterRequestBean reporterRequestBean = gson.fromJson(body, ReporterRequestBean.class);
        Receiver receiver = new Receiver();
        ReceiverResponseBean receiverResponseBean = receiver.persist(reporterRequestBean);
        String response = gson.toJson(receiverResponseBean);

        return response;
    }
}
