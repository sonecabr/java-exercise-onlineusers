package com.thinkstep.test.onlineusers.log.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

@Value
@Builder
@Entity(name = "log_history")
public class ApacheCombinedLogLine implements LogLine {

    /**
     * Example line: 32.217.185.255 - - [04/06/2019:21:15:26 +0000] "HEAD /e-enable/visualize/infrastructures/revolutionize" 504 91944 "https://www.dynamicbenchmark.io/grow/utilize/vertical" "Mozilla/5.0 (iPad; CPU OS 7_1_2 like Mac OS X; en-US) AppleWebKit/536.42.2 (KHTML, like Gecko) Version/4.0.5 Mobile/8B113 Safari/6536.42.2"
     * %h is the remote host (ie the client IP)
     * %l is the identity of the user determined by identd (not usually used since not reliable)
     * %u is the user name determined by HTTP authentication
     * %t is the time the request was received.
     * %r is the request line from the client. ("GET / HTTP/1.0")
     * %>s is the status code sent from the server to the client (200, 404 etc.)
     * %b is the size of the response to the client (in bytes)
     */
    @Transient
    public static final String format = "%h %l %u %t \\\"%r\\\" %>s %b \\\"%{Referer}i\\\" \\\"%{User-agent}i\\\"\" combined";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String remoteHost;

    private String identity;

    private String userName;

    private Instant requestTime;

    private String method;

    private String request;

    private Integer statusCode;

    private Integer responseSize;

    private String referer;

    private String userAgent;

    private Instant ingestionTime;


    public String getEncodedUserAgent() {
        return Optional.ofNullable(this.userAgent)
                .map(us -> {
                    try {
                        return Base64.getEncoder().encodeToString(us.getBytes("utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        return "invalid-user-agent";
                    }
                })
                .orElse("");
    }


}
