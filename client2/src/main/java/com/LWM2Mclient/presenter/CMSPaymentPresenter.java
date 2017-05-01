package com.LWM2Mclient.presenter;

import com.LWM2Mclient.model.clientObject.POS;
import com.LWM2Mclient.model.entity.serverEntity.PaymentResponseBean;
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
@RequestMapping("/CMS")
class CMSPaymentPresenter {

    private Logger logger = Logger.getLogger(CMSPaymentPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping("/pay")
    private String payBill() {

        logger.info("Pay to server.");

        POS pos = new POS();
        PaymentResponseBean paymentResponseBean = pos.pay();
        String response = gson.toJson(paymentResponseBean);

        return response;
    }
}