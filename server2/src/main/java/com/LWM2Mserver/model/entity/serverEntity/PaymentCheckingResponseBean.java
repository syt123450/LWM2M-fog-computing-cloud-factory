package com.LWM2Mserver.model.entity.serverEntity;

import lombok.Data;

/**
 * Created by ss on 2017/4/26.
 */

@Data
public class PaymentCheckingResponseBean {

    private boolean checkResult;
    private String checkResultMessage;
}
