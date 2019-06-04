package com.thinkstep.test.onlineusers.log.processor;

import com.thinkstep.test.onlineusers.log.LogLine;

import java.util.concurrent.Future;

@FunctionalInterface
public interface LogProcessor {

    Future<LogProcessorResult> submit(LogLine format, String line);
}
