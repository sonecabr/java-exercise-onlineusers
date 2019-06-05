package com.thinkstep.test.onlineusers.log.processor;

import com.thinkstep.test.onlineusers.log.LogLine;
import com.thinkstep.test.onlineusers.log.collector.LogFileProcessException;

import java.io.InputStream;


public interface LogProcessor {

    LogLine submit(LogLine format, String line);

    Boolean isFileChanged(InputStream file, String folder, String fileName) throws LogFileProcessException;

    Boolean isMD5Changed(InputStream is, String referenceMd5) throws LogFileProcessException;
}
