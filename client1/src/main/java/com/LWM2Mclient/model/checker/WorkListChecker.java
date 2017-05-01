package com.LWM2Mclient.model.checker;

import com.LWM2Mclient.model.utils.MysqlUtils;
import com.LWM2Mclient.model.entity.clientEntity.operation.JobBean;
import com.LWM2Mclient.model.entity.clientEntity.operation.WorkListBean;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by ss on 2017/4/2.
 */
public class WorkListChecker {

    static private Logger logger = Logger.getLogger(WorkListChecker.class);

    public WorkListBean check() {

        WorkListBean workListBean = new WorkListBean();
        ArrayList<JobBean> workListData = MysqlUtils.readWorkList();
        workListBean.setWorkList(workListData);

        logger.info("The work list bean is " + workListBean);

        return workListBean;
    }
}
