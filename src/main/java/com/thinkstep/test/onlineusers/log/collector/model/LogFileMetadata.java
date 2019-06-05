package com.thinkstep.test.onlineusers.log.collector.model;


import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Data
@Entity(name = "log_file_metadata")
@Builder
public class LogFileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String folder;

    private String fileName;

    private String md5;

    private Long lastProcessedLine;

    private Instant lastUpdate;
}
