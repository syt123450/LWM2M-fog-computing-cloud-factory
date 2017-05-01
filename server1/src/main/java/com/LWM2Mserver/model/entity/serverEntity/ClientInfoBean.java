package com.LWM2Mserver.model.entity.serverEntity;

import com.LWM2Mserver.model.entity.clientEntity.ResourceAddressBean;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when webui request for data of the client, this is the datatype of each user
 */

@Data
public class ClientInfoBean {

    private String clientName;
    private String lifeTime;
    private String version;
    private String bindingMode;
    private String address;
    private ArrayList<ResourceAddressBean> resourceAddressList;
}
