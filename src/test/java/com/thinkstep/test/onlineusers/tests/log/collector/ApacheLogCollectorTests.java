package com.thinkstep.test.onlineusers.tests.log.collector;

import com.thinkstep.test.onlineusers.base.TestSuite;
import com.thinkstep.test.onlineusers.log.collector.ApacheLogCollector;
import com.thinkstep.test.onlineusers.log.collector.LogFileProcessException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class ApacheLogCollectorTests {


    @Autowired
    private ApacheLogCollector apacheLogCollector;

    private String logFolderPath = "apache-logs";

    @Test
    public void shouldListOnlyLogFiles() {
        try {
            Resource folder = new ClassPathResource(logFolderPath);
            List<String> files = apacheLogCollector.scanForLogFiles(((ClassPathResource) folder).getURL().getPath(), "log");
            files.stream().forEach(item -> {
                Assert.assertTrue(item.matches("^.*\\.(log)$"));
            });
        } catch (LogFileProcessException | IOException e) {
            e.printStackTrace();
        }
    }
}
