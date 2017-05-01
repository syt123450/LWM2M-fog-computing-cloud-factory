package com.LWM2Mserver.model.entity.clientEntity;

import lombok.Data;

/**
 * Created by ss on 2017/4/23.
 */

@Data
public class ReporterRequestBean {

    private String clientName;
    private String executorStatus;
    private String workingStatus;
    private int JobID;
    private int leftNum;
}
