package com.bootstrapServer.model.entity.CMSEntity;

import lombok.Data;

/**
 * Created by ss on 2017/3/5.
 */

@Data
public class CMSAddRequestBean {

    String serverName;
    String newRegistrationUrl;
    String updateRegistrationUrl;
    String deRegistrationUrl;
    String reporterUrl;
}
