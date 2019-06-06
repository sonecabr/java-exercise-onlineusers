package com.thinkstep.test.onlineusers.tests.log.collector;

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

    @Before
    public void setUp(){}

    @After
    public void tearDown(){}

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

    @Test
    public void shouldMatchComibinedLogOnly() {
        String regex = "/ˆ(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+)\\s?(\\S+)?\\s?(\\S+)?\" (\\d{3}|-) (\\d+|-)\\s?\"?([^\"]*)\"?\\s?\"?([^\"]*)?\"?$/gm";
        String logLine = "6.223.143.229 - - [05/06/2019:18:23:35 +0000] \"POST /reinvent\" 400 16336 \"http://www.corporateefficient.com/visualize/compelling\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0 rv:5.0; en-US) AppleWebKit/534.26.7 (KHTML, like Gecko) Version/5.2 Safari/534.26.7\"";
        Pattern pattern = Pattern.compile(regex);
        Assert.assertTrue(pattern.matcher(logLine).matches());
        Assert.assertEquals(pattern.matcher(logLine).groupCount(), 11);
    }
}
