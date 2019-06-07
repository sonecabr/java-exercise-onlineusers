package com.thinkstep.test.onlineusers.metrics.repository;

import com.thinkstep.test.onlineusers.metrics.model.OnlineUserMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


public interface OnlineUserMetricRepository extends
        CrudRepository<OnlineUserMetric, Long> {}
