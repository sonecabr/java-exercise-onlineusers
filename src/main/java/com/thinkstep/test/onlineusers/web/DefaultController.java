package com.thinkstep.test.onlineusers.web;


import com.thinkstep.test.onlineusers.metrics.business.OnlineUserMetricManager;
import com.thinkstep.test.onlineusers.metrics.model.OnlineUserMetric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api")
@Slf4j
public class DefaultController {

    @Autowired
    private OnlineUserMetricManager onlineUserMetricManager;

    @RequestMapping("/onlineusers")
    public OnlineUserMetric getCurrentCounters() {
        return onlineUserMetricManager.getMetrics();
    }

}
