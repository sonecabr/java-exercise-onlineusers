package com.thinkstep.test.onlineusers.tests.metrics.business;


import com.thinkstep.test.onlineusers.log.model.ApacheCombinedLogLine;
import com.thinkstep.test.onlineusers.log.model.LogLine;
import com.thinkstep.test.onlineusers.log.processor.ApacheCombinedLogProcessor;
import com.thinkstep.test.onlineusers.metrics.business.OnlineUserMetricManager;
import com.thinkstep.test.onlineusers.metrics.model.OnlineUserMetric;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class OnlineUserMetricManagerTest {


    @Autowired
    private OnlineUserMetricManager onlineUserMetricManager;

    @Autowired
    private ApacheCombinedLogProcessor apacheCombinedLogProcessor;


    @Before
    public void setUp(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");
        LogLine line5seconds =
                apacheCombinedLogProcessor.serialize(
                        "88.100.96.3 - - [" + formatter.format(currentTime) +" +0000] \"HEAD /robust/best-of-breed/real-time/integrated\" 500 82627 \"https://www.customerdistributed.biz/value-added\" \"Mozilla/5.0 (Windows CE) AppleWebKit/5330 (KHTML, like Gecko) Chrome/40.0.831.0 Mobile Safari/5330\"");

        apacheCombinedLogProcessor.submit(line5seconds);


        LogLine line1minute =
                apacheCombinedLogProcessor.serialize(
                        "88.100.96.4 - - [" + formatter.format(currentTime.minus(30, ChronoUnit.SECONDS)) +" +0000] \"HEAD /robust/best-of-breed/real-time/integrated\" 500 82627 \"https://www.customerdistributed.biz/value-added\" \"Mozilla/5.0 (Windows CE) AppleWebKit/5330 (KHTML, like Gecko) Chrome/40.0.831.0 Mobile Safari/5330\"");


        apacheCombinedLogProcessor.submit(line1minute);

        LogLine line5minutes =
                apacheCombinedLogProcessor.serialize(
                        "88.100.96.5 - - [" + formatter.format(currentTime.minus(3, ChronoUnit.MINUTES)) +" +0000] \"HEAD /robust/best-of-breed/real-time/integrated\" 500 82627 \"https://www.customerdistributed.biz/value-added\" \"Mozilla/5.0 (Windows CE) AppleWebKit/5330 (KHTML, like Gecko) Chrome/40.0.831.0 Mobile Safari/5330\"");

        apacheCombinedLogProcessor.submit(line5minutes);

        LogLine line10minutes =
                apacheCombinedLogProcessor.serialize(
                        "88.100.96.6 - - [" + formatter.format(currentTime.minus(7, ChronoUnit.MINUTES)) +" +0000] \"HEAD /robust/best-of-breed/real-time/integrated\" 500 82627 \"https://www.customerdistributed.biz/value-added\" \"Mozilla/5.0 (Windows CE) AppleWebKit/5330 (KHTML, like Gecko) Chrome/40.0.831.0 Mobile Safari/5330\"");


        apacheCombinedLogProcessor.submit(line10minutes);

        LogLine line30minutes =
                apacheCombinedLogProcessor.serialize(
                        "88.100.96.7 - - [" + formatter.format(currentTime.minus(20, ChronoUnit.MINUTES)) +" +0000] \"HEAD /robust/best-of-breed/real-time/integrated\" 500 82627 \"https://www.customerdistributed.biz/value-added\" \"Mozilla/5.0 (Windows CE) AppleWebKit/5330 (KHTML, like Gecko) Chrome/40.0.831.0 Mobile Safari/5330\"");


        apacheCombinedLogProcessor.submit(line30minutes);

        LogLine line1Hour =
                apacheCombinedLogProcessor.serialize(
                        "88.100.96.8 - - [" + formatter.format(currentTime.minus(40, ChronoUnit.MINUTES)) +" +0000] \"HEAD /robust/best-of-breed/real-time/integrated\" 500 82627 \"https://www.customerdistributed.biz/value-added\" \"Mozilla/5.0 (Windows CE) AppleWebKit/5330 (KHTML, like Gecko) Chrome/40.0.831.0 Mobile Safari/5330\"");

        apacheCombinedLogProcessor.submit(line1Hour);

        LogLine line1Day =
                apacheCombinedLogProcessor.serialize(
                        "88.100.96.9 - - [" + formatter.format(currentTime.minus(2, ChronoUnit.HOURS)) +" +0000] \"HEAD /robust/best-of-breed/real-time/integrated\" 500 82627 \"https://www.customerdistributed.biz/value-added\" \"Mozilla/5.0 (Windows CE) AppleWebKit/5330 (KHTML, like Gecko) Chrome/40.0.831.0 Mobile Safari/5330\"");

        apacheCombinedLogProcessor.submit(line1Day);

    }

    @After
    public void tearDown(){}

    @Test
    public void shouldListUniqueUsers(){
        OnlineUserMetric ref =
                OnlineUserMetric
                        .builder()
                        .past5Secons(1)
                        .past1Minute(2)
                        .past5Minutes(3)
                        .past30Minutes(5)
                        .past1Hour(6)
                        .past1Day(7)
                        .build();

        OnlineUserMetric onlineUserMetric = onlineUserMetricManager.getMetrics();
        Assert.assertEquals(onlineUserMetric.getPast5Secons(), ref.getPast5Secons());
    }
}
