package com.LWM2Mclient.model.clientObject;

import com.LWM2Mclient.model.entity.clientEntity.operation.IdentityBean;
import com.LWM2Mclient.model.entity.serverEntity.PaymentResponseBean;
import com.LWM2Mclient.model.utils.MysqlUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/26.
 */
public class POS {

    static private String PAY_ERROR_MESSAGE = "Some error happens when process payment.";
    private Logger logger = Logger.getLogger(POS.class);
    private Gson gson = new GsonBuilder().create();

    public PaymentResponseBean pay() {

        PaymentResponseBean paymentResponseBean = new PaymentResponseBean();

        try {
            IdentityBean identityBean = new IdentityBean();
            identityBean.getIdentity();
            String paymentUrl = MysqlUtils.getCentralServerResource("paymentUrl");

            String message = gson.toJson(identityBean);

            HttpEntity httpEntity = new StringEntity(message);
            String responseContent = Request.Post(paymentUrl).body(httpEntity).execute().returnContent().asString();

            logger.info("Pay Response content is: " + responseContent);

            paymentResponseBean = gson.fromJson(responseContent, PaymentResponseBean.class);
        } catch (Exception e) {
            paymentResponseBean.setResultMessage(PAY_ERROR_MESSAGE);
        }

        return paymentResponseBean;
    }
}
