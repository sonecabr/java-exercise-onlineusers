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

    private Boolean isMD5Changed(InputStream is, String referenceMd5) throws LogFileProcessException {
        try {
            return !referenceMd5.equals(DigestUtils.md5DigestAsHex(is));
        } catch (IOException e) {
            throw new LogFileProcessException(e);
        }
    }
}
