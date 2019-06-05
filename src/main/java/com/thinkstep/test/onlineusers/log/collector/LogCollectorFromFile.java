package com.thinkstep.test.onlineusers.log.collector;

import java.util.List;

@FunctionalInterface
public interface LogCollectorFromFile {

    List<String> readFileContent(String path, String encoding, Long offset) throws LogFileProcessException;
}
