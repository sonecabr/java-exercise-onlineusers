package com.thinkstep.test.onlineusers.log.collector.business;


import com.thinkstep.test.onlineusers.log.collector.LogFileProcessException;
import com.thinkstep.test.onlineusers.log.collector.model.LogFileMetadata;
import com.thinkstep.test.onlineusers.log.collector.repository.LogFileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.InputStream;

import java.time.Instant;


@Service
public class LogFileMetadataStorageService {

    @Autowired
    private LogFileMetadataRepository repository;


}
