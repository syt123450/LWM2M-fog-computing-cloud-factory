package com.LWM2Mserver.model.entity.clientEntity;

import lombok.Data;

/**
 * Created by ss on 2017/4/2.
 */

@Data
public class ResourceAddressBean {

    private int resourceID;
    private String resourceName;
    private String operation;
    private String address;
}