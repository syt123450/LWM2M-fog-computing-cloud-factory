package com.LWM2Mclient.model.clientObject;

import com.LWM2Mclient.model.entity.clientEntity.operation.OperationMessageBean;
import com.LWM2Mclient.model.utils.MongodbUtils;
import com.LWM2Mclient.model.utils.MysqlUtils;

/**
 * Created by ss on 2017/4/22.
 */
public class Discover {

    public OperationMessageBean find() {

        String status = MysqlUtils.readResourceStatus("workListHolder");

        OperationMessageBean operationMessageBean = new OperationMessageBean();
        operationMessageBean.setResultMessage(status);

        return operationMessageBean;
    }
}
