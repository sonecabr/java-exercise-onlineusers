package com.thinkstep.test.onlineusers.log.processor;

import com.thinkstep.test.onlineusers.log.ApacheCombinedLogLine;
import com.thinkstep.test.onlineusers.log.LogLine;
import com.thinkstep.test.onlineusers.log.collector.LogFileProcessException;
import com.thinkstep.test.onlineusers.log.collector.model.LogFileMetadata;
import com.thinkstep.test.onlineusers.log.collector.repository.LogFileMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ApacheCombinedLogProcessor implements LogProcessor {

    @Autowired
    private LogFileMetadataRepository repository;

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

    public LogLine serialize(String logLine) {
        String[] log = logLine.split(" ");
        StringBuffer userAgent = new StringBuffer("");
        for(int i = 10; i < log.length; i++){
            userAgent.append(log[i]);
            if(i < log.length -1) {
                userAgent.append(" ");
            }
        }
        return ApacheCombinedLogLine
                .builder()
                .remoteHost(log[0])
                .identity(log[1])
                .userName(log[2])
                .requestTime(
                    Instant.from(ZonedDateTime.of(
                            LocalDateTime.from(
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss +000").parse((
                                            log[3] + " " +log[4]).substring(1,25))),
                            ZoneId.systemDefault())))
                .method(log[5])
                .request(log[6])
                .statusCode(new Integer(log[7]))
                .responseSize(new Integer(log[8]))
                .referer(log[9])
                .userAgent(userAgent.toString())
                .build();
    }

    public Boolean isFileChanged(InputStream file, String folder, String fileName) throws LogFileProcessException {
        LogFileMetadata meta = repository.findByFileName(folder, fileName);
        if(meta != null) {
            if(meta.getLastUpdate().isAfter(Instant.now()) || meta.getLastUpdate().equals(Instant.now())){
                return false;
            }

            if(isMD5Changed(file, meta.getMd5())){
                return false;
            }
        }

        return true;
    }

    public Boolean isMD5Changed(InputStream is, String referenceMd5) throws LogFileProcessException {
        try {
            return !referenceMd5.equals(DigestUtils.md5DigestAsHex(is));
        } catch (IOException e) {
            throw new LogFileProcessException(e);
        }
    }
}
