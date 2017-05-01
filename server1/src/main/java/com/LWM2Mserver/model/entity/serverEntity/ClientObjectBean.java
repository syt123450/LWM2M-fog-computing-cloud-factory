package com.LWM2Mserver.model.entity.serverEntity;

import lombok.Data;

/**
 * Created by ss on 2017/4/23.
 */

@Data
public class ClientObjectBean {

    String serverName;
    String clientName;
    String clientObjectName;
    String clientObjectStatus;
    String connectStatus;
}