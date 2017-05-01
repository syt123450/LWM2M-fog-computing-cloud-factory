package com.bootstrapServer.model.entity.serverEntity;

import lombok.Data;
import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * use as the bootstrap data's dataStructure which sends back to client
 */

@Data
public class BootstrapResponseBean {
    
    boolean write;
    ArrayList<ServerInfoBean> writeList;
    String delete;
    ArrayList<String> deleteList;
    String information;
    boolean centralServer;
    CentralServerInfoBean centralServerInfoBean;
}