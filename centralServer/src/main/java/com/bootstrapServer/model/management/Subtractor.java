package com.bootstrapServer.model.management;

import com.bootstrapServer.model.entity.CMSEntity.CMSDeleteRequestBean;
import com.bootstrapServer.model.entity.serverEntity.CMSOperationResponseBean;
import com.bootstrapServer.model.utils.MongodbUtils;

/**
 * Created by ss on 2017/3/5.
 */

public class Subtractor {

    static private String errMessage = "The server does not existed.";
    static private String successMessage = "Delete successfully.";

    public CMSOperationResponseBean sub(CMSDeleteRequestBean cmsDeleteRequestBean) {

        CMSOperationResponseBean cmsOperationResponseBean = new CMSOperationResponseBean();
        String serverName = cmsDeleteRequestBean.getServerName();

        if (MongodbUtils.findSpecificServer(serverName)) {
            MongodbUtils.deleteServer(serverName);
            cmsOperationResponseBean.setInformation(successMessage);
        }
        else {
            cmsOperationResponseBean.setInformation(errMessage);
        }

        return cmsOperationResponseBean;
    }
}