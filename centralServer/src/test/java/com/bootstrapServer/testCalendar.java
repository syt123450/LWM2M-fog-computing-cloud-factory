package com.bootstrapServer;

import org.junit.Test;

import java.util.Calendar;

/**
 * Created by ss on 2017/4/26.
 */
public class testCalendar {

    @Test
    public void test() {
        Calendar now = Calendar.getInstance();
        System.out.println(now.getTime());
        System.out.println(now.getTimeInMillis());
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
        System.out.println(now.getTime());
        now.setTimeInMillis(1493218984069l);
        System.out.println(now.getTime());
    }
}
