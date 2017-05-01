package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.clientObject.ReportProvider;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ss on 2017/4/25.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/CMS/report")
class CMSReportPresenter {

    private Logger logger = Logger.getLogger(CMSRegistrationPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/create")
    private String createReport() {

        logger.info("Get create report request from CMS.");

        ReportProvider reportProvider = new ReportProvider();
        OperationMessageBean operationMessageBean = reportProvider.create();
        String response = gson.toJson(operationMessageBean);

        return response;
    }
}