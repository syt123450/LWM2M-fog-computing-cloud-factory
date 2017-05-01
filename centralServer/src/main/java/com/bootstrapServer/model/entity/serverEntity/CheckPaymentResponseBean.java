package com.bootstrapServer.model.entity.serverEntity;

import lombok.Data;

/**
 * Created by ss on 2017/4/25.
 */

@Data
public class CheckPaymentResponseBean {

    private boolean checkResult;
    private String checkResultMessage;
}