package com.LWM2Mclient.model.entity.clientEntity.operation;

import lombok.Data;

import java.util.ArrayList;
import com.LWM2Mclient.model.entity.clientEntity.operation.JobBean;

/**
 * Created by ss on 2017/4/2.
 */

@Data
public class WorkListBean {

    private ArrayList<JobBean> workList;
}
