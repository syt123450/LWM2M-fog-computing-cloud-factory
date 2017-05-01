package com.LWM2Mserver.model.entity.clientEntity;

import lombok.Data;
import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when client request for newRegistration function, this is the datatype that client sends to server
 */

@Data
public class NewRegistrationBean {

    private String clientName;
    private int lifeTime;
    private String version;
    private String bindingMode;
    private String address;
    private ArrayList<ResourceAddressBean> resourceAddressList;
}
