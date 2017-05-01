package com.LWM2Mserver.model.entity.CMSEntity;

import lombok.Data;

/**
 * Created by ss on 2017/3/3.
 */

/**
 * when webUI request the one client information, this is the datatype that xmlhttprequest send to server
 */


@Data
public class CheckOneRequestBean {

    String clientName;
}