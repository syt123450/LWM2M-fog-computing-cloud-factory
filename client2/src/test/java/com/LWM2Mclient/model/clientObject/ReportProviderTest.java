package com.LWM2Mclient.model.clientObject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/4/27.
 */
public class ReportProviderTest {

    @Test
    public void testCreate() {
        ReportProvider reportProvider = new ReportProvider();
        System.out.println(reportProvider.create());
    }
}