package com.thinkstep.test.onlineusers.log.processor;

import com.thinkstep.test.onlineusers.log.ApacheCombinedLogLine;
import com.thinkstep.test.onlineusers.log.LogLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
public class ApacheCombinedLogProcessor implements LogProcessor {


    @Value("${reactor.pool.size}")
    private Integer reactorPoolSize = 10;

    private final Scheduler logIngestionScheduler =
            Schedulers.newParallel(ApacheCombinedLogProcessor.class.getSimpleName().toLowerCase(), reactorPoolSize, true);

    @Override
    public LogLine submit(LogLine format, String line) {

        final Mono<Mono<Object>> callableMono =
                Mono.fromCallable(()-> {
                    return Mono.empty();
                });

        return ApacheCombinedLogLine.builder().build();
    }
}
