package com.LWM2Mserver;

import com.LWM2Mserver.model.utils.MongodbUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ss on 2017/3/6.
 */
public class CheckLifeTime {

    @Test
    public void checkLifeTime() throws Exception{
        while (true) {
            ArrayList<String> clientNameList = MongodbUtils.getAllClientName();
            for (int i = 0; i < clientNameList.size(); i++) {
                String clientName = clientNameList.get(i);
                String timeStamp = MongodbUtils.getClientAttribute(clientName, "timeStamp");
                String lifeTime = MongodbUtils.getClientAttribute(clientName, "lifeTime");

                if (calculateOvertime(timeStamp, lifeTime)) {
                    MongodbUtils.deleteClient(clientName);
                }
            }

            Thread.sleep(60000);
        }
    }

    private boolean calculateOvertime(String timeStamp, String lifeTime) {

        float lastTime = Float.parseFloat(timeStamp);
        float recordLifeTime = Float.parseFloat(lifeTime);
        float timeNow = Float.parseFloat(String.valueOf(new Date().getTime()));

        return (timeNow - lastTime)/1000 > recordLifeTime;
    }

}
