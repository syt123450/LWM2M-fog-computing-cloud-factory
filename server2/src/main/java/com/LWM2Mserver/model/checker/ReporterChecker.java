package com.LWM2Mserver.model.checker;

import com.LWM2Mserver.model.entity.CMSEntity.CheckReportRequestBean;
import com.LWM2Mserver.model.entity.serverEntity.ReporterInfoBean;
import com.LWM2Mserver.model.utils.MongodbUtils;

import java.util.ArrayList;

/**
 * Created by ss on 2017/4/24.
 */
public class ReporterChecker {

    public ArrayList<ReporterInfoBean> check(CheckReportRequestBean checkReportRequestBean) {

        String clientName = checkReportRequestBean.getClientName();
        return MongodbUtils.getReport(clientName);
    }
}
