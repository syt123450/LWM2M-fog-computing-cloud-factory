package com.LWM2Mserver.model.entity.serverEntity;

import lombok.Data;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when client request for any register function, this is the datatype that server sends back to client
 */

@Data
public class RegistrationResponseBean {

    String operation;
    String information;
}
