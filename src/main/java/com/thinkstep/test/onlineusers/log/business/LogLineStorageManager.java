package com.thinkstep.test.onlineusers.log.business;

import com.thinkstep.test.onlineusers.log.model.ApacheCombinedLogLine;
import com.thinkstep.test.onlineusers.log.model.LogLine;
import com.thinkstep.test.onlineusers.log.repository.ApacheCombinedLogLineRepository;
import com.thinkstep.test.onlineusers.metrics.model.OnlineUserMetric;
import com.thinkstep.test.onlineusers.metrics.repository.OnlineUserMetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class LogLineStorageManager {

    @Autowired
    private ApacheCombinedLogLineRepository logLineRepository;

    @Autowired
    private OnlineUserMetricRepository onlineUserMetricRepository;


    @CachePut(value = "logs")
    public LogLine add(LogLine logLine) {
        return logLineRepository.save((ApacheCombinedLogLine) logLine);
    }

    @Cacheable(
            value = "consolidations",
            key = "#cacheKey"
    )
    public OnlineUserMetric consolidate(String cacheKey) {
        Integer past5seconds = logLineRepository.findUniqueByPeriod(Instant.now().minus(5, ChronoUnit.SECONDS), Instant.now()).size();
        Integer past1Minute = logLineRepository.findUniqueByPeriod(Instant.now().minus(1, ChronoUnit.MINUTES), Instant.now()).size();
        Integer past5Minutes = logLineRepository.findUniqueByPeriod(Instant.now().minus(5, ChronoUnit.MINUTES), Instant.now()).size();
        Integer past30Minutes = logLineRepository.findUniqueByPeriod(Instant.now().minus(30, ChronoUnit.MINUTES), Instant.now()).size();
        Integer past1Hour = logLineRepository.findUniqueByPeriod(Instant.now().minus(1, ChronoUnit.HOURS), Instant.now()).size();
        Integer past1Day = logLineRepository.findUniqueByPeriod(Instant.now().minus(1, ChronoUnit.DAYS), Instant.now()).size();

        OnlineUserMetric currentMetric = OnlineUserMetric
                .builder()
                .past5Secons(past5seconds)
                .past1Minute(past1Minute)
                .past5Minutes(past5Minutes)
                .past30Minutes(past30Minutes)
                .past1Hour(past1Hour)
                .past1Day(past1Day)
                .timestamp(Instant.now())
                .build();

        onlineUserMetricRepository.save(currentMetric);

        return currentMetric;


    }

}
