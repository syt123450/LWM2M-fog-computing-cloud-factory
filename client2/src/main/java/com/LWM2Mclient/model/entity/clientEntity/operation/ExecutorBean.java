package com.LWM2Mclient.model.entity.clientEntity.operation;

import lombok.Data;

/**
 * Created by ss on 2017/4/24.
 */

@Data
public class ExecutorBean {

    private Thread thread;
    private boolean operation;
    private OperationMessageBean operationMessageBean;
}
