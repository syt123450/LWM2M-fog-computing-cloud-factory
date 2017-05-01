package com.LWM2Mserver.model.serverObject;

import com.LWM2Mserver.model.entity.serverEntity.ReportCollectRequestBean;
import com.LWM2Mserver.model.entity.serverEntity.ReporterInfoBean;
import com.LWM2Mserver.model.utils.MongodbUtils;

import java.util.ArrayList;

/**
 * Created by ss on 2017/4/26.
 */
public class Collector {

    public ArrayList<ReporterInfoBean> collect(ReportCollectRequestBean reportCollectRequestBean) {

        String clientName = reportCollectRequestBean.getClientName();
        return MongodbUtils.getReport(clientName);
    }
}
