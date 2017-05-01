package com.bootstrapServer.model.management;

import com.bootstrapServer.model.entity.serverEntity.CMSOperationResponseBean;
import com.bootstrapServer.model.entity.CMSEntity.CMSAddRequestBean;
import com.bootstrapServer.model.utils.MongodbUtils;

import java.util.HashMap;

/**
 * Created by ss on 2017/3/5.
 */

public class Adder {

    static private String errMessage = "The server is already existed.";
    static private String successMessage = "Add successfully.";

    public CMSOperationResponseBean add(CMSAddRequestBean cmsAddRequestBean) {

        CMSOperationResponseBean cmsOperationResponseBean = new CMSOperationResponseBean();
        String serverName = cmsAddRequestBean.getServerName();

        if (MongodbUtils.findSpecificServer(serverName)) {
            cmsOperationResponseBean.setInformation(errMessage);
        }
        else {
            HashMap<String, String> serverInfoHashMap = new HashMap<>();
            serverInfoHashMap.put("serverName", serverName);
            serverInfoHashMap.put("newRegistrationUrl", cmsAddRequestBean.getNewRegistrationUrl());
            serverInfoHashMap.put("updateRegistrationUrl", cmsAddRequestBean.getUpdateRegistrationUrl());
            serverInfoHashMap.put("deRegistrationUrl", cmsAddRequestBean.getDeRegistrationUrl());
            serverInfoHashMap.put("reporterUrl", cmsAddRequestBean.getReporterUrl());
            MongodbUtils.createServer(serverInfoHashMap);
            cmsOperationResponseBean.setInformation(successMessage);
        }

        return cmsOperationResponseBean;
    }
}
