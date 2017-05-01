package com.LWM2Mclient.model.utils;

import com.LWM2Mclient.model.entity.serverEntity.BootstrapDataBean;
import com.LWM2Mclient.model.entity.serverEntity.CentralServerInfoBean;
import com.LWM2Mclient.model.entity.serverEntity.ServerInfoBean;
import java.util.ArrayList;

/**
 * Created by ss on 2017/3/5.
 */
public class BootstrapPersistenceUtils {

    static public void persist(BootstrapDataBean bootstrapDataBean) throws Exception{

        String delete = bootstrapDataBean.getDelete();
        boolean write = bootstrapDataBean.isWrite();
        boolean centralServer = bootstrapDataBean.isCentralServer();
        ArrayList<ServerInfoBean> writeList = bootstrapDataBean.getWriteList();
        ArrayList<String> deleteList = bootstrapDataBean.getDeleteList();
        CentralServerInfoBean centralServerInfoBean = bootstrapDataBean.getCentralServerInfoBean();

        if (delete.equals("All")) {
            MysqlUtils.deleteAllServer();
        }
        else if (delete.equals("One")) {
            MysqlUtils.deleteServer(deleteList.get(0));
        }

        if (write) {
            MysqlUtils.persistAllServer(writeList);
        }

        if (centralServer) {
            MysqlUtils.deleteCentralServer();
            MysqlUtils.persistCentralServer(centralServerInfoBean);
        }
    }
}