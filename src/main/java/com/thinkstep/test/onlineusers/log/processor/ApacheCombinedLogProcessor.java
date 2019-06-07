package com.thinkstep.test.onlineusers.log.processor;

import com.thinkstep.test.onlineusers.log.business.LogLineStorageManager;
import com.thinkstep.test.onlineusers.log.model.ApacheCombinedLogLine;
import com.thinkstep.test.onlineusers.log.model.LogLine;
import com.thinkstep.test.onlineusers.log.collector.LogFileProcessException;
import com.thinkstep.test.onlineusers.log.collector.model.LogFileMetadata;
import com.thinkstep.test.onlineusers.log.collector.repository.LogFileMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import org.springframework.util.DigestUtils;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Service
public class ApacheCombinedLogProcessor implements LogProcessor {

    @Autowired
    private LogFileMetadataRepository logFileMetadataRepository;

    @Autowired
    private LogLineStorageManager logLineStorageManager;

    @Value("${reactor.pool.size}")
    private Integer reactorPoolSize = 10;

    @Value("${logs.apache.regex}")
    private String logRegex;


    private final Scheduler logIngestionScheduler =
            Schedulers.newParallel(ApacheCombinedLogProcessor.class.getSimpleName().toLowerCase(), reactorPoolSize, true);

    public LogFileMetadata save(LogFileMetadata logFileMetadata) {
        return logFileMetadataRepository.save(logFileMetadata);
    }

    public void submit(List<String> lines) {
        lines.parallelStream().forEach(line -> {
            logLineStorageManager.add(serialize(line));
        });
    }

    @Override
    public LogLine submit(LogLine logLine) {

        return logLineStorageManager.add(logLine);
    }

    public LogLine serialize(String logLine) {
        Pattern pattern = Pattern.compile(logRegex);
        Matcher matcher = pattern.matcher(logLine);
        matcher.matches();
        String[] logArr = new String[11];
        for(int i = 0; i < matcher.groupCount(); i++) {
            logArr[i] = matcher.group(i+1);
        }

        try {
            return ApacheCombinedLogLine
                    .builder()
                    .remoteHost(logArr[0])
                    .identity(logArr[1])
                    .userName(logArr[2])
                    .requestTime(
                            Instant.from(ZonedDateTime.of(
                                    LocalDateTime.from(
                                            DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss +0000")
                                                    .parse(logArr[3])),
                                    ZoneId.systemDefault())))
                    .method(logArr[4])
                    .request(logArr[5])
                    .statusCode(new Integer(Optional.ofNullable(logArr[7]).orElse("0")))
                    .responseSize(new Integer(Optional.ofNullable(logArr[8]).orElse("0")))
                    .referer(logArr[9])
                    .userAgent(logArr[10])
                    .ingestionTime(Instant.now())
                    .build();
        } catch (Exception e) {
            log.error("Error on line serialization", e);
            return null;
        }

    }

    public LogFileMetadata getFileMetadata(String folder, String fileName) {
        List<LogFileMetadata> meta = logFileMetadataRepository.findByFileName(folder, fileName);
        if(meta != null && meta.size() > 0) {
            return meta.get(0);
        }
        return LogFileMetadata
                .builder()
                .folder(folder)
                .fileName(fileName)
                .lastProcessedLine(0l)
                .build();
    }

    public Boolean isFileChanged(InputStream file, String folder, String fileName, Long lastProcessedLine) throws LogFileProcessException {
        List<LogFileMetadata> meta = logFileMetadataRepository.findByFileName(folder, fileName);

        try {

            if(meta != null && meta.size() > 0) {
                if(meta.get(0).getLastUpdate().isAfter(Instant.now()) || meta.get(0).getLastUpdate().equals(Instant.now())){
                    return false;
                }

                if(isMD5Changed(file, meta.get(0).getMd5())){
                    return false;
                }
            }

            if(meta != null && meta.size() > 0) {
                meta.get(0).setMd5(DigestUtils.md5DigestAsHex(file));
                logFileMetadataRepository.save(meta.get(0));
            } else {
                logFileMetadataRepository.save(LogFileMetadata
                        .builder()
                        .fileName(fileName)
                        .folder(folder)
                        .lastProcessedLine(lastProcessedLine)
                        .md5(DigestUtils.md5DigestAsHex(file))
                        .lastUpdate(Instant.now())
                        .build()
                );
            }


        } catch (IOException e) {
            throw new LogFileProcessException(e);
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

    @Override
    public List<LogLine> getLogs() {
        return null;
    }

    @Override
    @Cacheable(
            key = "#remoteHost::#uniqueId::userId::encodedUserAgent",
            value = "logs"
    )
    public List<LogLine> getLogs(String remoteHost, String uniqueId, String userId, String encodedUserAgent) {
        return null;
    }
}
