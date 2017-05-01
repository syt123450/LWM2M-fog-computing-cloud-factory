package com.bootstrapServer.model.entity.serverEntity;

import lombok.Data;

/**
 * Created by ss on 2017/4/25.
 */

@Data
public class PaymentInfoBean {

    private String clientName;
    private int requestTime;
    private String formula;
    private long dueDate;
}