package com.LWM2Mclient.model.entity.CMSEntity;

import lombok.Data;
import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when webUI is doing updateRegistration, this dataType is webUI collect for client
 */

@Data
public class UpdateRegistrationRequestBean {

    private String serverName;
    private String lifeTime;
    private String version;
    private String bindingMode;
    private ArrayList<UpdateRequestBean> updateRequestBeans;
}
