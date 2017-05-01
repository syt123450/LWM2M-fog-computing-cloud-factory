package com.LWM2Mclient.model.utils;

import com.LWM2Mclient.model.entity.clientEntity.register.UpdateRegistrationBean;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by ss on 2017/3/5.
 */
public class MongodbUtilsTest {

    @Test
    @Ignore
    public void testDeleteAllServer() throws Exception {
        MongodbUtils.deleteAllServer();
    }

    @Test
    @Ignore
    public void testDeleteServer() throws Exception {
        MongodbUtils.deleteServer("server1");
        MongodbUtils.deleteServer("server");
    }

    @Test
    @Ignore
    public void persistAllServer() throws Exception {

    }

    @Test
    @Ignore
    public void persistServer() throws Exception {

    }

    @Test
    @Ignore
    public void checkServer() throws Exception {
        System.out.println(MongodbUtils.checkServer("server1"));
        System.out.println(MongodbUtils.checkServer("server"));
    }

    @Test
    @Ignore
    public void testGetNewRegistrationData() {

        System.out.println(MongodbUtils.getNewRegistrationData());
    }

    @Test
    public void testUpdateRegistrationInfo() {
        UpdateRegistrationBean updateRegistrationBean = new UpdateRegistrationBean();
        updateRegistrationBean.setClientName("client1");
        updateRegistrationBean.setLifeTime("86400.0");
        updateRegistrationBean.setVersion("2.0");
        updateRegistrationBean.setBindingMode("U");
        MongodbUtils.updateRegistrationInfo(updateRegistrationBean);
    }
}