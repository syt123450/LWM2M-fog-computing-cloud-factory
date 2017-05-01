package com.LWM2Mclient.model.utils;

import com.LWM2Mclient.model.entity.serverEntity.CentralServerInfoBean;
import com.LWM2Mclient.model.entity.serverEntity.ServerInfoBean;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by ss on 2017/4/2.
 */
public class MysqlUtilsTest {

    @Test
    @Ignore
    public void testCheckResourceStatus() {
        System.out.println(MysqlUtils.readResourceStatus("listholder"));
    }

    @Test
    @Ignore
    public void testUpdateResourceStatus() {
        MysqlUtils.updateResourceStatus("listholder", "off");
    }

    @Test
    @Ignore
    public void testReadWorkList() {
        System.out.println(MysqlUtils.readWorkList());
    }

    @Test
    @Ignore
    public void testDeleteJob() {
        System.out.println(MysqlUtils.deleteJob(1));
    }

    @Test
    @Ignore
    public void testAddJob() {
        ArrayList jobArgs = new ArrayList();
        jobArgs.add("green");
        jobArgs.add("big");
        jobArgs.add("cute");
        jobArgs.add(100);

        MysqlUtils.addJob(jobArgs);
    }

    @Test
    @Ignore
    public void testGetOneServerInfo() {
        System.out.println(MysqlUtils.getOneServerInfo("server1", "newRegistrationUrl"));
    }

    @Test
    @Ignore
    public void testGetClientInstanceInfo() {
        System.out.println(MysqlUtils.getClientInstanceInfo());
    }

    @Test
    @Ignore
    public void testGetClientResourceInstanceInfo() {
        System.out.println(MysqlUtils.getClientResourceInstanceInfo());
    }

    @Test
    @Ignore
    public void testGetResourceOperationInfo() {
        System.out.println(MysqlUtils.getResourceOperationInfo(0, "turn_on"));
    }

    @Test
    @Ignore
    public void testPersistAllServer() {
        ServerInfoBean serverInfoBean = new ServerInfoBean();
        serverInfoBean.setServerName("11");
        serverInfoBean.setNewRegistrationUrl("aa");
        serverInfoBean.setUpdateRegistrationUrl("bb");
        serverInfoBean.setDeRegistrationUrl("cc");
        ArrayList<ServerInfoBean> arrayList = new ArrayList<>();
        arrayList.add(serverInfoBean);
        MysqlUtils.persistAllServer(arrayList);
    }

    @Test
    @Ignore
    public void testDeleteServer() {
        MysqlUtils.deleteServer("11");
    }

    @Test
    @Ignore
    public void testGetBootstrapInfo() {
        System.out.println(MysqlUtils.getBootstrapInfo());
    }

    @Test
    @Ignore
    public void testSetEnvironment() {
        MysqlUtils.setEnvironment("Bad");
    }

    @Test
    @Ignore
    public void testGetEnvironment() {
        System.out.println(MysqlUtils.getEnvironment());
    }

    @Test
    @Ignore
    public void testGetJobNow() {
        System.out.println(MysqlUtils.getJobNow());
    }

    @Test
    @Ignore
    public void testDeleteJobItem() {
        MysqlUtils.deleteJobItem();
    }

    @Test
    @Ignore
    public void testPersistCentralServer() {
        CentralServerInfoBean centralServerInfoBean = new CentralServerInfoBean();
        centralServerInfoBean.setPaymentUrl("11");
        centralServerInfoBean.setCreateReportUrl("22");
        centralServerInfoBean.setDownLoadReportUrl("33");
        MysqlUtils.persistCentralServer(centralServerInfoBean);
    }

    @Test
    @Ignore
    public void testDeleteCentralServer() {
        MysqlUtils.deleteCentralServer();
    }

    @Test
    @Ignore
    public void testGetCentralServerResource() {
        System.out.println(MysqlUtils.getCentralServerResource("paymentUrl"));
    }

    @Test
    public void testGetClientName() {
        System.out.println(MysqlUtils.getClientName());
    }
}