package com.LWM2Mserver.model.utils;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by ss on 2017/3/3.
 */
public class mongodbUtilsTest {

    @Test
    @Ignore
    public void testDelete() {
        System.out.println(MongodbUtils.deleteClient("222"));
    }

    @Test
    @Ignore
    public void testGetClientInfo() {
        System.out.println(MongodbUtils.getClientInfo("client1"));
    }

    @Test
    @Ignore
    public void testGetAllClientsInfo() {

        System.out.println(MongodbUtils.getAllClientsInfo());
    }

    @Test
    @Ignore
    public void testDeleteClient() {
        MongodbUtils.deleteClient("client");
    }

    @Test
    @Ignore
    public void testGetClientAttirbute() {
        System.out.println(MongodbUtils.getClientAttribute("client1","timeStamp"));
        System.out.println(MongodbUtils.getClientAttribute("client1","lifeTime"));
    }

    @Test
    @Ignore
    public void testGetAllClientName() {
        System.out.println(MongodbUtils.getAllClientName());
    }

    @Test
    @Ignore
    public void testInsertClient() {
        HashMap newClientInfo = new HashMap();
        newClientInfo.put("clientName", "client");
        newClientInfo.put("lifeTime", 86400);
        newClientInfo.put("version", "1.0");
        newClientInfo.put("bindingMode", "U");
        newClientInfo.put("address", "http://localhost:8200");
        newClientInfo.put("timeStamp", String.valueOf(new Date().getTime()));

        MongodbUtils.insertClient(newClientInfo);
    }

    @Test
    @Ignore
    public void testInsertResource() {
        HashMap newResourceInfo = new HashMap();
        newResourceInfo.put("clientName", "client");
        newResourceInfo.put("resourceID", 0);
        newResourceInfo.put("resourceName", "executor");
        newResourceInfo.put("operation", "turn_on");
        newResourceInfo.put("address", "/executor/on");

        MongodbUtils.insertResourceOperation(newResourceInfo);
    }

    @Test
    @Ignore
    public void testIsResourceOperationExist() {
        System.out.println(MongodbUtils.isResourceOperationExist("client1", "0","add_job"));
        System.out.println(MongodbUtils.isResourceOperationExist("client1", "0","XXX"));
    }

    @Test
    @Ignore
    public void testDeleteResourceOperation() {
        MongodbUtils.deleteResourceOperation("client1", "0","add_job");
    }

    @Test
    @Ignore
    public void testGetClientResources() {
        System.out.println(MongodbUtils.getClientResources("client1"));
    }

    @Test
    @Ignore
    public void testGetResourceOperationUrl() {
        System.out.println(MongodbUtils.getResourceOperationUrl("client1","workListHolder", "delete_job"));
    }

    @Test
    public void testGetReport() {
        System.out.println(MongodbUtils.getReport("client1"));
    }
}