package com.thinkstep.test.onlineusers.metrics.model;

import lombok.Builder;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity(name = "online_user_metric")
@Value
@Builder
public class OnlineUserMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Instant timestamp;

    private Integer past5Secons;

    private Integer past1Minute;

    private Integer past5Minutes;

    private Integer past30Minutes;

    private Integer past1Hour;

    private Integer past1Day;
}
