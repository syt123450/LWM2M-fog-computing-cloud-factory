package com.LWM2Mserver.presenter;

import java.util.ArrayList;

import com.LWM2Mserver.model.checker.ReporterChecker;
import com.LWM2Mserver.model.entity.CMSEntity.CheckReportRequestBean;
import com.LWM2Mserver.model.entity.serverEntity.ReporterInfoBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.LWM2Mserver.model.checker.ClientInfoChecker;
import com.LWM2Mserver.model.entity.CMSEntity.CheckOneRequestBean;
import com.LWM2Mserver.model.entity.serverEntity.ClientInfoBean;

/**
 * Created by ss on 2017/3/3.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/CMS/check")
class CMSCheckingPresenter {

    private Logger logger = Logger.getLogger(CMSCheckingPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/oneClient", method = RequestMethod.POST)
    private String checkForOne(@RequestBody String body) {

        CheckOneRequestBean checkOneRequestBean = gson.fromJson(body, CheckOneRequestBean.class);
        ClientInfoChecker clientInfoChecker = new ClientInfoChecker();
        ClientInfoBean clientInfoBean = clientInfoChecker.getOneClient(checkOneRequestBean);
        String response = gson.toJson(clientInfoBean);

        return response;
    }

    @RequestMapping("/allClients")
    private String checkForAll () {

        ClientInfoChecker clientInfoChecker = new ClientInfoChecker();
        ArrayList<ClientInfoBean> arrayList = clientInfoChecker.getAllClient();
        String response = gson.toJson(arrayList);

        return response;
    }

    @RequestMapping(value = "/reporter", method = RequestMethod.POST)
    private String checkReport(@RequestBody String body) {

        CheckReportRequestBean checkReportRequestBean = gson.fromJson(body, CheckReportRequestBean.class);
        ReporterChecker reporterChecker = new ReporterChecker();
        ArrayList<ReporterInfoBean> reporterInfoBeans = reporterChecker.check(checkReportRequestBean);
        String response = gson.toJson(reporterInfoBeans);

        return response;
    }
}