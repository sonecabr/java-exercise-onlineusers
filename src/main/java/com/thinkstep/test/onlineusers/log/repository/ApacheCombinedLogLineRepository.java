package com.thinkstep.test.onlineusers.log.repository;


import com.thinkstep.test.onlineusers.log.model.ApacheCombinedLogLine;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.time.Instant;
import java.util.List;


public interface ApacheCombinedLogLineRepository extends
        CrudRepository<ApacheCombinedLogLine, Long> {

    @Query(value = "SELECT CONCAT(identity, user_name, remote_host, user_agent), COUNT(*) FROM log_history WHERE requestTime >= ?1 AND requestTime <= ?2 group by CONCAT(identity, user_name, remote_host, user_agent)")
    List<ApacheCombinedLogLine> findUniqueByPeriod(Instant from, Instant to);

}
