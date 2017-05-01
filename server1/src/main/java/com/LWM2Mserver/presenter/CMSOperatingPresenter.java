package com.LWM2Mserver.presenter;

import com.LWM2Mserver.model.checker.ResourceChecker;
import com.LWM2Mserver.model.entity.CMSEntity.ReadAttributeRequestBean;
import com.LWM2Mserver.model.entity.CMSEntity.SwitchResourceRequestBean;
import com.LWM2Mserver.model.entity.clientEntity.OperationMessageBean;
import com.LWM2Mserver.model.entity.clientEntity.ResourceAttributeBean;
import com.LWM2Mserver.model.serverObject.Switcher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/2.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/CMS/operator")
class CMSOperatingPresenter {

    private Logger logger = Logger.getLogger(CMSOperatingPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/switcher", method = RequestMethod.POST)
    private String switchResource(@RequestBody String body) {

        SwitchResourceRequestBean switchResourceRequestBean = gson.fromJson(body, SwitchResourceRequestBean.class);
        Switcher switcher = new Switcher();
        OperationMessageBean operationMessageBean = switcher.switchResource(switchResourceRequestBean);
        String response = gson.toJson(operationMessageBean);

        return response;
    }

    @RequestMapping(value = "/readAttribute", method = RequestMethod.POST)
    private String readAttribute(@RequestBody String body) {

        ReadAttributeRequestBean readAttributeRequestBean = gson.fromJson(body, ReadAttributeRequestBean.class);
        ResourceChecker resourceChecker = new ResourceChecker();
        ResourceAttributeBean resourceAttributeBean = resourceChecker.check(readAttributeRequestBean);
        String response = gson.toJson(resourceAttributeBean);

        return response;
    }
}
