package com.bootstrapServer.model.entity.serverEntity;

import java.util.ArrayList;

import com.bootstrapServer.model.entity.clientEntity.ServerInitiatedResponseBean;
import lombok.Data;

/**
 * Created by ss on 2017/3/5.
 */

@Data
public class CMSBootstrapResultBean {

    ArrayList<ServerInitiatedResponseBean> resultList;
}
