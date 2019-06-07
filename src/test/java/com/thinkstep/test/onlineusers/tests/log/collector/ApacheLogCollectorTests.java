package com.thinkstep.test.onlineusers.tests.log.collector;


import com.thinkstep.test.onlineusers.log.collector.ApacheLogCollector;
import com.thinkstep.test.onlineusers.log.collector.LogFileProcessException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class ApacheLogCollectorTests {


    @Autowired
    private ApacheLogCollector apacheLogCollector;

    private String logFolderPath = "classpath:apache-logs";

    @Test
    public void shouldTranslateFolderFromClassPath() {
        try {
            List<String> files = apacheLogCollector.scanForLogFiles(logFolderPath, "log");
            files.stream().forEach(item -> {
                Assert.assertTrue(item.matches("^.*\\.(log)$"));
            });
        } catch (LogFileProcessException e) {
            e.printStackTrace();
        }
    }
}
