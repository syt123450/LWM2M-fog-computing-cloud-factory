package com.bootstrapServer.model.entity.serverEntity;

import lombok.Data;

/**
 * Created by ss on 2017/4/24.
 */

@Data
public class ReporterInfoBean {

    private String clientName;
    private String executorStatus;
    private String workingStatus;
    private int JobID;
    private int leftNum;
    private String time;
}