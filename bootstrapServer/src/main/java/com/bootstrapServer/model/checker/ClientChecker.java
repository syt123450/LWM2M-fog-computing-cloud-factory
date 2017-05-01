package com.bootstrapServer.model.checker;

import com.bootstrapServer.model.entity.serverEntity.ClientInfoBean;
import com.bootstrapServer.model.utils.MongodbUtils;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/5.
 */
public class ClientChecker {

    public ArrayList<ClientInfoBean> check() {

        return MongodbUtils.getAllClientInfo();
    }
}