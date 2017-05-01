package com.LWM2Mclient.model.entity.clientEntity.bootstrap;

import com.LWM2Mclient.model.utils.PropertiesUtils;
import lombok.Data;

/**
 * Created by ss on 2017/3/5.
 */

@Data
public class ServerInitiatedResponseBean {

    String clientName;
    String bootResult;

    public void initiateData() {

        clientName = PropertiesUtils.getProperty("name");
    }
}
