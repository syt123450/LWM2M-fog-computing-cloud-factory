package com.LWM2Mclient.model.entity.clientEntity.register;

import lombok.Data;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * this is the dataType use for response how registration process
 */

@Data
public class RegistrationResultBean {

    String serverName;
    String clientName;
    String registrationInfo;
}
