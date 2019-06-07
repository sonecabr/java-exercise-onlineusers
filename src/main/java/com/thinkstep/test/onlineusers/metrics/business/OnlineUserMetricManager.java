package com.thinkstep.test.onlineusers.metrics.business;

import com.thinkstep.test.onlineusers.log.business.LogLineStorageManager;
import com.thinkstep.test.onlineusers.metrics.model.OnlineUserMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Service
public class OnlineUserMetricManager {

    @Autowired
    private LogLineStorageManager logLineStorageManager;

    public OnlineUserMetric getMetrics() {

        return logLineStorageManager.consolidate(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMANY)
                        .withZone( ZoneId.systemDefault()).format(Instant.now()));

    }
}
