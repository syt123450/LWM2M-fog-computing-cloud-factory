package com.bootstrapServer.model.checker;

import com.bootstrapServer.model.entity.serverEntity.ServerInfoBean;
import com.bootstrapServer.model.utils.MongodbUtils;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/5.
 */
public class BootstrapChecker {

    public ArrayList<ServerInfoBean> check() {

        return MongodbUtils.getAllBootstrapData();
    }
}
