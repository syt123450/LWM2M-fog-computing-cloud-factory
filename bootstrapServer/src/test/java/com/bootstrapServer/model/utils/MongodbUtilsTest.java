package com.bootstrapServer.model.utils;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/3/4.
 */

public class MongodbUtilsTest {

    @Test
    @Ignore
    public void testCheckClient() {

        System.out.println(MongodbUtils.checkClient("client"));
        System.out.println(MongodbUtils.checkClient("client1"));
    }

    @Test
    @Ignore
    public void testGetAllServerName () {
        System.out.println(MongodbUtils.getAllServerName());
    }

    @Test
    @Ignore
    public void testGetAllBootstrapData() {
        System.out.println(MongodbUtils.getAllBootstrapData());
    }

    @Test
    @Ignore
    public void testDeleteServer() {
        MongodbUtils.deleteServer("hello");
    }

    @Test
    @Ignore
    public void testFindSpecificServer() {
        System.out.println(MongodbUtils.findSpecificServer("server2"));
        System.out.println(MongodbUtils.findSpecificServer("server"));
    }

    @Test
    @Ignore
    public void testGetCentralServerInfo() {
        System.out.println(MongodbUtils.getCentralServerInfo());
    }

    @Test
    @Ignore
    public void testUpdateLeftRequestTime() {
        MongodbUtils.updateLeftRequestTime("client1");
    }

    @Test
    @Ignore
    public void testChangeBasicUserStatus() {
        MongodbUtils.changeBasicUserStatus("client1");
    }

    @Test
    @Ignore
    public void testUpdatePremiumUser() {
        MongodbUtils.updatePremiumUser("client1", 1495812283275l);
    }

    @Test
    @Ignore
    public void testGetReportMappingUrl() {
        System.out.println(MongodbUtils.getReportMappingUrl());
    }
}