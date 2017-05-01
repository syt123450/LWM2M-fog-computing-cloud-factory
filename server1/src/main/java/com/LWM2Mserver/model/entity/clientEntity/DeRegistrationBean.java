package com.LWM2Mserver.model.entity.clientEntity;

import lombok.Data;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when client requests the deregistration function, this is the datatype client sends to server
 */

@Data
public class DeRegistrationBean {

    String clientName;
}
