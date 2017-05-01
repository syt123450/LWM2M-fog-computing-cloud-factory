package com.LWM2Mserver.presenter;

import com.LWM2Mserver.model.entity.serverEntity.ReportCollectRequestBean;
import com.LWM2Mserver.model.entity.serverEntity.ReporterInfoBean;
import com.LWM2Mserver.model.serverObject.Collector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by ss on 2017/4/26.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/central")
class CentralLogPresenter {

    private Logger logger = Logger.getLogger(CentralLogPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    private String getReport(@RequestBody String body) {

        logger.info("Receive report collect instruction from central server.");

        ReportCollectRequestBean reportCollectRequestBean = gson.fromJson(body, ReportCollectRequestBean.class);
        Collector collector = new Collector();
        ArrayList<ReporterInfoBean> reporterInfoBeans = collector.collect(reportCollectRequestBean);
        String response = gson.toJson(reporterInfoBeans);

        return response;
    }
}
