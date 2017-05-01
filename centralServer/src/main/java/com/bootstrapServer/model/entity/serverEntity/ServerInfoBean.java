package com.bootstrapServer.model.entity.serverEntity;

import lombok.Data;

/**
 * Created by ss on 2017/3/4.
 */

@Data
public class ServerInfoBean {

    String serverName;
    String newRegistrationUrl;
    String updateRegistrationUrl;
    String deRegistrationUrl;
    String reporterUrl;
}