package com.LWM2Mserver.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;
import com.LWM2Mserver.model.registration.*;
import com.LWM2Mserver.model.entity.clientEntity.NewRegistrationBean;
import com.LWM2Mserver.model.entity.clientEntity.UpdateRegistrationBean;
import com.LWM2Mserver.model.entity.clientEntity.DeRegistrationBean;
import com.LWM2Mserver.model.entity.serverEntity.RegistrationResponseBean;

/**
 * Created by ss on 2017/3/3.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/register")
class RegisterPresenter {

    private Logger logger = Logger.getLogger(RegisterPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/newRegister", method = RequestMethod.POST)
    private String newRegister(@RequestBody String body) {

        NewRegistrationBean newRegistrationBean = gson.fromJson(body, NewRegistrationBean.class);
        NewKeygen newKeygen = new NewKeygen();
        RegistrationResponseBean registrationResponseBean = newKeygen.registerToServer(newRegistrationBean);
        String response = gson.toJson(registrationResponseBean);

        return response;
    }

    @RequestMapping(value = "/updateRegister", method = RequestMethod.POST)
    private String updateRegister(@RequestBody String body) {

        UpdateRegistrationBean updateRegistrationBean = gson.fromJson(body, UpdateRegistrationBean.class);
        UpdateKeygen updateRegister = new UpdateKeygen();
        RegistrationResponseBean registrationResponseBean = updateRegister.updateClientInfo(updateRegistrationBean);
        String response = gson.toJson(registrationResponseBean);

        return response;
    }

    @RequestMapping(value = "/deRegister", method = RequestMethod.POST)
    private String deRegister(@RequestBody String body) {

        DeRegistrationBean deRegistrationBean = gson.fromJson(body, DeRegistrationBean.class);
        DeKeygen deKeygen = new DeKeygen();
        RegistrationResponseBean registrationResponseBean = deKeygen.removeForServer(deRegistrationBean);
        String response = gson.toJson(registrationResponseBean);

        return response;
    }
}
