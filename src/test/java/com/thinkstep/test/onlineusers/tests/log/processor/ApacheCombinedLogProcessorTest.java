package com.thinkstep.test.onlineusers.tests.log.processor;

import com.thinkstep.test.onlineusers.log.model.ApacheCombinedLogLine;
import com.thinkstep.test.onlineusers.log.model.LogLine;
import com.thinkstep.test.onlineusers.log.processor.ApacheCombinedLogProcessor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class ApacheCombinedLogProcessorTest {

    @Before
    public void setUp(){}

    @After
    public void tearDown(){}

    @Autowired
    private ApacheCombinedLogProcessor apacheCombinedLogProcessor;


    @Test
    public void shouldSerializeLogLine() {
        LogLine ref1 =
                ApacheCombinedLogLine
                        .builder()
                        .remoteHost("128.33.254.99")
                        .identity("-")
                        .userName("-")
                        .userAgent("\"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_9_4 rv:6.0) Gecko/1952-26-05 Firefox/35.0\"")
                        .build();

        LogLine ref2 =
                ApacheCombinedLogLine
                        .builder()
                        .remoteHost("202.109.81.118")
                        .identity("-")
                        .userName("powlowski8588")
                        .userAgent("\"Mozilla/5.0 (X11; Linux x86_64; rv:5.0) Gecko/1932-11-09 Firefox/36.0\"")
                        .build();

        LogLine ref3 =
                ApacheCombinedLogLine
                        .builder()
                        .remoteHost("117.98.11.82")
                        .identity("-")
                        .userName("thiel5026")
                        .userAgent("\"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_7_10 rv:4.0) Gecko/1984-07-11 Firefox/35.0\"")
                        .build();
        String[] logLineStr =
                {
                        "128.33.254.99 - - [05/06/2019:22:31:49 +0000] \"PATCH /redefine/vertical/paradigms\" 502 12911 \"https://www.forwardenvisioneer.biz/harness/sexy\" \"Mozilla/5.0 (Macintosh; PPC Mac OS X 10_9_4 rv:6.0) Gecko/1952-26-05 Firefox/35.0\"",
                        "202.109.81.118 - powlowski8588 [05/06/2019:22:31:49 +0000] \"DELETE /frictionless\" 400 47824 \"http://www.corporatebleeding-edge.name/models/customized\" \"Mozilla/5.0 (X11; Linux x86_64; rv:5.0) Gecko/1932-11-09 Firefox/36.0\"",
                        "117.98.11.82 - thiel5026 [05/06/2019:22:31:49 +0000] \"PATCH /implement/next-generation/frictionless/compelling\" 406 69443 \"https://www.corporateharness.com/value-added\" \"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_7_10 rv:4.0) Gecko/1984-07-11 Firefox/35.0\""
                };

        for(int i = 0; i < 3; i++) {
            ApacheCombinedLogLine logLine = (ApacheCombinedLogLine) apacheCombinedLogProcessor.serialize(logLineStr[i]);
            if(i == 0) {
                Assert.assertEquals(logLine.getRemoteHost(), ((ApacheCombinedLogLine) ref1).getRemoteHost());
                Assert.assertEquals(logLine.getIdentity(), ((ApacheCombinedLogLine) ref1).getIdentity());
                Assert.assertEquals(logLine.getUserName(), ((ApacheCombinedLogLine) ref1).getUserName());
                Assert.assertEquals(logLine.getUserAgent(), ((ApacheCombinedLogLine) ref1).getUserAgent());
            }
            if(i == 1) {
                Assert.assertEquals(logLine.getRemoteHost(), ((ApacheCombinedLogLine) ref2).getRemoteHost());
                Assert.assertEquals(logLine.getIdentity(), ((ApacheCombinedLogLine) ref2).getIdentity());
                Assert.assertEquals(logLine.getUserName(), ((ApacheCombinedLogLine) ref2).getUserName());
                Assert.assertEquals(logLine.getUserAgent(), ((ApacheCombinedLogLine) ref2).getUserAgent());
            }
            if(i == 2) {
                Assert.assertEquals(logLine.getRemoteHost(), ((ApacheCombinedLogLine) ref3).getRemoteHost());
                Assert.assertEquals(logLine.getIdentity(), ((ApacheCombinedLogLine) ref3).getIdentity());
                Assert.assertEquals(logLine.getUserName(), ((ApacheCombinedLogLine) ref3).getUserName());
                Assert.assertEquals(logLine.getUserAgent(), ((ApacheCombinedLogLine) ref3).getUserAgent());
            }
        }

    }
}
