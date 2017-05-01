package com.LWM2Mserver.model.checker;

import com.LWM2Mserver.model.entity.CMSEntity.ReadAttributeRequestBean;
import com.LWM2Mserver.model.entity.clientEntity.ResourceAttributeBean;
import com.LWM2Mserver.model.entity.clientEntity.WorkListBean;
import com.LWM2Mserver.model.utils.MongodbUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;

/**
 * Created by ss on 2017/4/2.
 */
public class ResourceChecker {

    private static String OPERATION = "read_attribute";
    private Logger logger = Logger.getLogger(ResourceChecker.class);
    private Gson gson = new GsonBuilder().create();

    public ResourceAttributeBean check(ReadAttributeRequestBean readAttributeRequestBean) {

        String clientName = readAttributeRequestBean.getClientName();
        String resourceName = readAttributeRequestBean.getResourceName();
        String resourceUrl = MongodbUtils.getResourceOperationUrl(clientName, resourceName, OPERATION);

        logger.info("The resource url is: " + resourceUrl);

        ResourceAttributeBean resourceAttributeBean = new ResourceAttributeBean();
        try {
            String responseContent = Request.Get(resourceUrl).execute().returnContent().asString();
            resourceAttributeBean = gson.fromJson(responseContent, ResourceAttributeBean.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("The resource attribute checking result is: " + resourceAttributeBean);

        return resourceAttributeBean;
    }
}
