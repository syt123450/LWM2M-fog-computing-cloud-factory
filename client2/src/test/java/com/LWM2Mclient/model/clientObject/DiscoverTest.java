package com.LWM2Mclient.model.clientObject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ss on 2017/4/22.
 */
public class DiscoverTest {

    @Test
    public void testFind() {
        Discover discover = new Discover();
        System.out.println(discover.find());
    }
}