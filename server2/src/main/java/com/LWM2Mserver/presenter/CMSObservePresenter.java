package com.LWM2Mserver.presenter;

import com.LWM2Mserver.model.entity.CMSEntity.ReportRequestBodyBean;
import com.LWM2Mserver.model.entity.serverEntity.ReporterResponseBean;
import com.LWM2Mserver.model.serverObject.ReportRequester;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/24.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/CMS/report")
class CMSObservePresenter {

    private Logger logger = Logger.getLogger(CMSObservePresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/observe", method = RequestMethod.POST)
    public String observe(@RequestBody String body) {

        logger.info("Observe the client.");

        ReportRequestBodyBean reportRequestBodyBean = gson.fromJson(body, ReportRequestBodyBean.class);
        ReportRequester reportRequester = new ReportRequester();
        ReporterResponseBean reporterResponseBean = reportRequester.observe(reportRequestBodyBean);
        String response = gson.toJson(reporterResponseBean);

        return response;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancelObservation(@RequestBody String body) {

        logger.info("Cancel observation.");

        ReportRequestBodyBean reportRequestBodyBean = gson.fromJson(body, ReportRequestBodyBean.class);
        ReportRequester reportRequester = new ReportRequester();
        ReporterResponseBean reporterResponseBean = reportRequester.cancel(reportRequestBodyBean);
        String response = gson.toJson(reporterResponseBean);

        return response;
    }
}
