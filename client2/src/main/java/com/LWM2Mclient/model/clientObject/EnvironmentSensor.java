package com.LWM2Mclient.model.clientObject;

import com.LWM2Mclient.model.entity.CMSEntity.ChangeEnvironmentBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import com.LWM2Mclient.model.utils.MysqlUtils;

/**
 * Created by ss on 2017/4/23.
 */
public class EnvironmentSensor {

    static private String SUCCESS_CHANGE_MESSAGE = "Successfully change the environment.";

    public OperationMessageBean change(ChangeEnvironmentBean changeEnvironmentBean) {

        String environment = changeEnvironmentBean.getEnvironment();
        MysqlUtils.setEnvironment(environment);

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        operationMessageBean.setResultMessage(SUCCESS_CHANGE_MESSAGE);

        return operationMessageBean;
    }

    public OperationMessageBean check() {

        String environment = MysqlUtils.getEnvironment();
        OperationMessageBean operationMessageBean = new OperationMessageBean();
        operationMessageBean.setResultMessage("The environment now is: " + environment);

        return operationMessageBean;
    }
}