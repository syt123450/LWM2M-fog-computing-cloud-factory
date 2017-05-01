package com.LWM2Mclient.model.bootstrap;

import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import com.LWM2Mclient.model.utils.MysqlUtils;

/**
 * Created by ss on 2017/4/10.
 */
public class Deleter {

    static private String SUCCESS_MESSAGE = "Successfully delete the bootstrap information.";

    public OperationMessageBean delete() {

        MysqlUtils.deleteAllServer();
        OperationMessageBean operationMessageBean = new OperationMessageBean();
        operationMessageBean.setResultMessage(SUCCESS_MESSAGE);

        return operationMessageBean;
    }
}
