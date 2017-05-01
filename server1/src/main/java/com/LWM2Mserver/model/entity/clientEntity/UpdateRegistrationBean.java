package com.LWM2Mserver.model.entity.clientEntity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when client request for updateRegistration, this is the datatype client sends to server
 */

@Data
public class UpdateRegistrationBean {

    private String clientName;
    private String lifeTime;
    private String version;
    private String bindingMode;
    private ArrayList<ResourceUpdateBean> resourceAddressList;
}
