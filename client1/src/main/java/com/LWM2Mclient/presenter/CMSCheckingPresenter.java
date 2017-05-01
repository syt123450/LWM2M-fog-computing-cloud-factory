package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.entity.serverEntity.ServerInfoBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.LWM2Mclient.model.checker.*;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/CMS/check")
class CMSCheckingPresenter {

    private Logger logger = Logger.getLogger(CMSCheckingPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/bootstrap")
    private String checkBootstrapInfo () {

        BootstrapChecker bootstrapChecker = new BootstrapChecker();
        ArrayList<ServerInfoBean> bootstrapDataBean = bootstrapChecker.check();
        String response = gson.toJson(bootstrapDataBean);

        return response;
    }
}