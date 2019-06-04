package com.thinkstep.test.onlineusers.web;


import com.thinkstep.test.onlineusers.request.OnlineUserMetric;
import com.thinkstep.test.onlineusers.request.RequestCount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class DefaultController {

    @RequestMapping("/now")
    public OnlineUserMetric getCurrentCounters() {
        return OnlineUserMetric
                .builder()
                .id(1l)
                .timestamp(System.currentTimeMillis())
                .build();
    }

}
