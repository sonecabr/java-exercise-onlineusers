package com.thinkstep.test.onlineusers.log.collector;

import java.util.List;

@FunctionalInterface
public interface LogCollectorFromFile {

    List<String> readFileContent(String folder, String fileName, String encoding, Long offset) throws LogFileProcessException;
}
