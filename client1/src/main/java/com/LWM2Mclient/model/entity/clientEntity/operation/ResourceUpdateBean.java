package com.LWM2Mclient.model.entity.clientEntity.operation;

import lombok.Data;

/**
 * Created by ss on 2017/4/2.
 */

@Data
public class ResourceUpdateBean {

    private String resourceSwitch;
    private int resourceID;
    private String operation;
    private String address;
    private String resourceName;
}
