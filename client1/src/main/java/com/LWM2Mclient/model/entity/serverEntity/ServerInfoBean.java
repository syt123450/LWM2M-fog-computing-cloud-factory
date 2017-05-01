package com.LWM2Mclient.model.entity.serverEntity;

import lombok.Data;

/**
 * Created by ss on 2017/3/4.
 */

@Data
public class ServerInfoBean {

    private String serverName;
    private String newRegistrationUrl;
    private String updateRegistrationUrl;
    private String deRegistrationUrl;
    private String reporterUrl;
}