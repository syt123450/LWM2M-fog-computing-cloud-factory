package com.LWM2Mclient.model.checker;

import com.LWM2Mclient.model.entity.serverEntity.BootstrapDataBean;
import com.LWM2Mclient.model.entity.serverEntity.ServerInfoBean;
import com.LWM2Mclient.model.utils.MongodbUtils;
import com.LWM2Mclient.model.utils.MysqlUtils;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */
public class BootstrapChecker {

    public ArrayList<ServerInfoBean> check () {

        return MysqlUtils.getBootstrapInfo();
    }
}