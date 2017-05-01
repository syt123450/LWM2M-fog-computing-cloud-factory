package com.LWM2Mclient.model.entity.clientEntity.register;

import lombok.Data;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when client is doing deregistration, this is the datatype client sends to server
 */

@Data
public class DeRegistrationBean {

    String clientName;
}
