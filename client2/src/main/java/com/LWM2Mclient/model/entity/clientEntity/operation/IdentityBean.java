package com.LWM2Mclient.model.entity.clientEntity.operation;

import com.LWM2Mclient.model.utils.PropertiesUtils;
import lombok.Data;

/**
 * Created by ss on 2017/4/26.
 */

@Data
public class IdentityBean {

    private String clientName;

    public void getIdentity() {
        clientName = PropertiesUtils.getProperty("name");
    }
}