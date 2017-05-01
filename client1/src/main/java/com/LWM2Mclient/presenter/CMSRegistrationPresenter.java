package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.entity.CMSEntity.DeRegistrationRequestBean;
import com.LWM2Mclient.model.entity.CMSEntity.UpdateRegistrationRequestBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.LWM2Mclient.model.registrationRequester.*;
import com.LWM2Mclient.model.entity.clientEntity.register.RegistrationResultBean;
import com.LWM2Mclient.model.entity.CMSEntity.NewRegistrationRequestBean;

/**
 * Created by ss on 2017/3/3.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/CMS/register")
class CMSRegistrationPresenter {

    private Logger logger = Logger.getLogger(CMSRegistrationPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    private String newRegistrationController (@RequestBody String body) {

        NewRegistrationRequestBean newRegistrationRequestBean = gson.fromJson(body, NewRegistrationRequestBean.class);
        NewRegistrationRequester newRegistrationRequester = new NewRegistrationRequester();
        RegistrationResultBean registrationResultBean = newRegistrationRequester.register(newRegistrationRequestBean);
        String response = gson.toJson(registrationResultBean);

        return response;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    private String updateRegisterationController (@RequestBody String body) {

        UpdateRegistrationRequestBean updateRegistrationRequestBean = gson.fromJson(body, UpdateRegistrationRequestBean.class);
        UpdateRegistrationRequester updateRegistrationRequester = new UpdateRegistrationRequester();
        RegistrationResultBean registrationResultBean = updateRegistrationRequester.update(updateRegistrationRequestBean);
        String response = gson.toJson(registrationResultBean);

        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deRegistrationController (@RequestBody String body) {

        DeRegistrationRequestBean deRegistrationRequestBean = gson.fromJson(body, DeRegistrationRequestBean.class);
        DeRegistrationRequester deRegistrationRequester = new DeRegistrationRequester();
        RegistrationResultBean registrationResultBean = deRegistrationRequester.delete(deRegistrationRequestBean);
        String response = gson.toJson(registrationResultBean);

        return response;
    }
}