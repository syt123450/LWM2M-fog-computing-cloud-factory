package com.LWM2Mclient.model.entity.serverEntity;

/**
 * Created by ss on 2017/3/3.
 */

import java.util.ArrayList;

import lombok.Data;

/**
 * when server is doing server initiate bootstrap, this is the dataType server sends to client
 */

@Data
public class BootstrapDataBean {

    boolean write;
    ArrayList<ServerInfoBean> writeList;
    String delete;
    ArrayList<String> deleteList;
    boolean centralServer;
    CentralServerInfoBean centralServerInfoBean;
    String information;
}
