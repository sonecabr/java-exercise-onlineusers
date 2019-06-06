package com.thinkstep.test.onlineusers.log.processor;

import com.thinkstep.test.onlineusers.log.model.LogLine;
import com.thinkstep.test.onlineusers.log.collector.LogFileProcessException;

import java.io.InputStream;
import java.util.List;


public interface LogProcessor {

    LogLine submit(LogLine format);

    Boolean isFileChanged(InputStream file, String folder, String fileName, Long lastProcessedLine) throws LogFileProcessException;

    Boolean isMD5Changed(InputStream is, String referenceMd5) throws LogFileProcessException;

    List<LogLine> getLogs();

    List<LogLine> getLogs(String remoteHost, String uniqueId, String userId, String encodedUserAgent);
}
