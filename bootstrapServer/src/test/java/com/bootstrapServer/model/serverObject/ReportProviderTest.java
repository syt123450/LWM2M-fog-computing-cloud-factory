package com.bootstrapServer.model.serverObject;

import com.bootstrapServer.model.entity.clientEntity.ReportRequestBean;
import com.bootstrapServer.model.entity.serverEntity.ReporterInfoBean;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/4/27.
 */
public class ReportProviderTest {

    @Test
    public void testGenerate() {
        ReportRequestBean reportRequestBean = new ReportRequestBean();
        reportRequestBean.setClientName("client1");
        ReportProvider reportProvider = new ReportProvider();
        reportProvider.generate(reportRequestBean);
    }
}