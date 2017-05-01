package com.bootstrapServer.presenter;

import com.bootstrapServer.model.entity.clientEntity.PaymentRequestBean;
import com.bootstrapServer.model.entity.edgeServerEntity.CheckPaymentRequestBean;
import com.bootstrapServer.model.entity.serverEntity.CheckPaymentResponseBean;
import com.bootstrapServer.model.entity.serverEntity.ClientOperationResultBean;
import com.bootstrapServer.model.serverObject.POS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ss on 2017/4/25.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/payment")
class PaymentPresenter {

    private Logger logger = Logger.getLogger(PaymentPresenter.class);
    private Gson gson = new GsonBuilder().create();

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String check(@RequestBody String body) {

        logger.info("Check client payment status from edge server.");

        CheckPaymentRequestBean checkPaymentRequestBean = gson.fromJson(body, CheckPaymentRequestBean.class);
        POS pos = new POS();
        CheckPaymentResponseBean checkPaymentResponseBean = pos.check(checkPaymentRequestBean);
        String response = gson.toJson(checkPaymentResponseBean);

        return response;
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    private String pay(@RequestBody String body) {

        logger.info("Pay operation from client.");

        PaymentRequestBean paymentRequestBean = gson.fromJson(body, PaymentRequestBean.class);
        POS pos = new POS();
        ClientOperationResultBean clientOperationResultBean = pos.pay(paymentRequestBean);
        String response = gson.toJson(clientOperationResultBean);

        return response;
    }
}
