package com.bootstrapServer.model.entity.CMSEntity;

import lombok.Data;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when doing serverInitiate, this is the datatype CMS send to server
 */

@Data
public class ServerInitiateRequestBean {

    String clientName;
    String serverName;
}