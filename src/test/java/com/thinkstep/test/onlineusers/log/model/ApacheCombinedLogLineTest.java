package com.thinkstep.test.onlineusers.log.model;


import com.thinkstep.test.onlineusers.base.TestSuite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class ApacheCombinedLogLineTest {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows CE) AppleWebKit/5330 (KHTML, like Gecko) Chrome/40.0.831.0 Mobile Safari/5330";

    private static final String REF_BASE_64 = "TW96aWxsYS81LjAgKFdpbmRvd3MgQ0UpIEFwcGxlV2ViS2l0LzUzMzAgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvNDAuMC44MzEuMCBNb2JpbGUgU2FmYXJpLzUzMzA=";




    @Test
    public void shouldGetBase64EncodedUserAgent() {
        Assert.assertEquals(
                ApacheCombinedLogLine
                        .builder()
                        .userAgent(USER_AGENT)
                        .build()
                        .getEncodedUserAgent(),
                REF_BASE_64
        );



    }
}
