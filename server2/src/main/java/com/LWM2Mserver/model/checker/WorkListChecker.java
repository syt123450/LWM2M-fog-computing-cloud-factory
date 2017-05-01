package com.LWM2Mserver.model.checker;

import com.LWM2Mserver.model.entity.CMSEntity.CheckWorkListRequestBean;
import com.LWM2Mserver.model.entity.clientEntity.WorkListBean;
import com.LWM2Mserver.model.utils.MongodbUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/2.
 */
public class WorkListChecker {

    static private String RESOURCE = "workListHolder";
    static private String OPERATION = "read_list";
    private Logger logger = Logger.getLogger(WorkListChecker.class);
    private Gson gson = new GsonBuilder().create();

    public WorkListBean check(CheckWorkListRequestBean checkWorkListRequestBean) {

        String clientName = checkWorkListRequestBean.getClientName();
        String workListUrl = MongodbUtils.getResourceOperationUrl(clientName, RESOURCE, OPERATION);

        logger.info("WorkList checker url is: " + workListUrl);

        WorkListBean workListBean = new WorkListBean();

        try {
            String responseContent = Request.Get(workListUrl).execute().returnContent().asString();
            workListBean = gson.fromJson(responseContent, WorkListBean.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("The workList checking result is: " + workListBean);

        return workListBean;
    }
}