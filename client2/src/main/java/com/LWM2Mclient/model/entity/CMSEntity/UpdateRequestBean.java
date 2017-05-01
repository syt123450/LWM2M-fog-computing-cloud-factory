package com.LWM2Mclient.model.entity.CMSEntity;

import lombok.Data;

/**
 * Created by ss on 2017/4/2.
 */

@Data
public class UpdateRequestBean {

    private String resourceSwitch;
    private int resourceID;
    private String operation;
}
