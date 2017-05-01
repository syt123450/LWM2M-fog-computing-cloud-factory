package com.LWM2Mclient.model.entity.clientEntity.operation;

import lombok.Data;

/**
 * Created by ss on 2017/4/23.
 */

@Data
public class ReporterBean {

    private String clientName;
    private String executorStatus;
    private String workingStatus;
    private int JobID;
    private int leftNum;
}