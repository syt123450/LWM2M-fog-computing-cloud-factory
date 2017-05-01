package com.LWM2Mclient.model.entity.clientEntity.register;

import lombok.Data;
import java.util.ArrayList;
import com.LWM2Mclient.model.entity.clientEntity.register.ResourceAddressBean;

/**
 * Created by ss on 2017/3/3.
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
