package com.LWM2Mclient.model.checker;

import com.LWM2Mclient.model.entity.clientEntity.operation.ResourceAttributeBean;
import com.LWM2Mclient.model.utils.MysqlUtils;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/2.
 */


public class ResourceChecker {

    static private String SUCCESSMESSAGE = "The status of the %s is %s.";
    static private String FAILEDMESSAGE = "Failed to check the status of the resource.";

    static private Logger logger = Logger.getLogger(ResourceChecker.class);

    public ResourceAttributeBean check(String resourceName) {

        ResourceAttributeBean resourceAttributeBean = new ResourceAttributeBean();
        resourceAttributeBean.setResourceName(resourceName);
        String checkResult = MysqlUtils.readResourceStatus(resourceName);

        logger.info("The result of the check is " + checkResult);

        if (checkResult.equals("failed")) {
            resourceAttributeBean.setResourceStatus(FAILEDMESSAGE);
        }
        else {
            String resourceMessage = String.format(SUCCESSMESSAGE, resourceName, checkResult);
            resourceAttributeBean.setResourceStatus(resourceMessage);
        }

        return resourceAttributeBean;
    }
}
