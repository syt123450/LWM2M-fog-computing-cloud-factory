package com.bootstrapServer.model.serverObject;

import com.bootstrapServer.model.entity.clientEntity.PaymentRequestBean;
import com.bootstrapServer.model.entity.edgeServerEntity.CheckPaymentRequestBean;
import com.bootstrapServer.model.entity.serverEntity.CheckPaymentResponseBean;
import com.bootstrapServer.model.entity.serverEntity.ClientOperationResultBean;
import com.bootstrapServer.model.entity.serverEntity.PaymentInfoBean;
import com.bootstrapServer.model.utils.MongodbUtils;
import org.apache.log4j.Logger;

import java.util.Calendar;

/**
 * Created by ss on 2017/4/25.
 */
public class POS {

    static private String PAY_SUCCESS_MESSAGE = "Successfully pay the bill.";
    static private String PAY_FAILED_MESSAGE = "Failed to pay for the bill.";

    static private String CHECK_SUCCESS_MESSAGE = "User has right to use the service.";
    static private String BASIC_FAILED_MESSAGE = "Denied because run out basic query time.";
    static private String PREMIUM_FAILED_MESSAGE = "Denied because payment due.";
    static private String DEFAULT_FAILED_MESSAGE = "Some error happen when checking the payment.";

    private Logger logger = Logger.getLogger(POS.class);

    public ClientOperationResultBean pay(PaymentRequestBean paymentRequestBean) {

        String clientName = paymentRequestBean.getClientName();

        ClientOperationResultBean clientOperationResultBean = new ClientOperationResultBean();

        try {

            PaymentInfoBean paymentInfoBean = MongodbUtils.getPaymentInfo(clientName);
            if (paymentInfoBean.getFormula().equals("Basic")) {
                MongodbUtils.changeBasicUserStatus(clientName);
            } else {
                MongodbUtils.updatePremiumUser(clientName, paymentInfoBean.getDueDate());
            }
            clientOperationResultBean.setResultMessage(PAY_SUCCESS_MESSAGE);
        } catch (Exception e) {
            clientOperationResultBean.setResultMessage(PAY_FAILED_MESSAGE);
        }

        logger.info(clientOperationResultBean);

        return clientOperationResultBean;
    }

    public CheckPaymentResponseBean check(CheckPaymentRequestBean checkPaymentRequestBean) {

        String clientName = checkPaymentRequestBean.getClientName();

        CheckPaymentResponseBean checkPaymentResponseBean = new CheckPaymentResponseBean();

        try {

            PaymentInfoBean paymentInfoBean = MongodbUtils.getPaymentInfo(clientName);

            Calendar calendar = Calendar.getInstance();
            if (paymentInfoBean.getFormula().equals("Premium")) {
                if (calendar.getTimeInMillis() < paymentInfoBean.getDueDate()) {
                    checkPaymentResponseBean.setCheckResult(true);
                    checkPaymentResponseBean.setCheckResultMessage(CHECK_SUCCESS_MESSAGE);
                } else {
                    if (paymentInfoBean.getRequestTime() > 0) {
                        MongodbUtils.updateLeftRequestTime(clientName);
                        checkPaymentResponseBean.setCheckResult(true);
                        checkPaymentResponseBean.setCheckResultMessage(CHECK_SUCCESS_MESSAGE);
                    } else {
                        checkPaymentResponseBean.setCheckResult(false);
                        checkPaymentResponseBean.setCheckResultMessage(PREMIUM_FAILED_MESSAGE);
                    }
                }
            } else {
                if (paymentInfoBean.getRequestTime() > 0) {
                    MongodbUtils.updateLeftRequestTime(clientName);
                    checkPaymentResponseBean.setCheckResult(true);
                    checkPaymentResponseBean.setCheckResultMessage(CHECK_SUCCESS_MESSAGE);
                } else {
                    checkPaymentResponseBean.setCheckResult(false);
                    checkPaymentResponseBean.setCheckResultMessage(BASIC_FAILED_MESSAGE);
                }
            }

            checkPaymentResponseBean.setCheckResult(true);
            checkPaymentResponseBean.setCheckResultMessage(CHECK_SUCCESS_MESSAGE);
        } catch (Exception e) {
            checkPaymentResponseBean.setCheckResult(false);
            checkPaymentResponseBean.setCheckResultMessage(DEFAULT_FAILED_MESSAGE);
        }

        return checkPaymentResponseBean;
    }
}