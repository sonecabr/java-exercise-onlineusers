package com.thinkstep.test.onlineusers.log;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApacheCombinedLogLine implements LogLine {

    /**
     * Example line: 32.217.185.255 - - [04/06/2019:21:15:26 +0000] "HEAD /e-enable/visualize/infrastructures/revolutionize" 504 91944 "https://www.dynamicbenchmark.io/grow/utilize/vertical" "Mozilla/5.0 (iPad; CPU OS 7_1_2 like Mac OS X; en-US) AppleWebKit/536.42.2 (KHTML, like Gecko) Version/4.0.5 Mobile/8B113 Safari/6536.42.2"
     */
    private static final String format = "%h %l %u %t \\\"%r\\\" %>s %b \\\"%{Referer}i\\\" \\\"%{User-agent}i\\\"\" combined";

    private Long time;

}
