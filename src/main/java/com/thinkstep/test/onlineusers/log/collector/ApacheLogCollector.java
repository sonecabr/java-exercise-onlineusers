package com.thinkstep.test.onlineusers.log.collector;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class ApacheLogCollector implements LogCollectorFromFile {

    private ResourceLoader resourceLoader;

    @Value("${logs.apache.folder}")
    private String logFolder;

    @Value("${logs.apache.encoding}")
    private String logEncoding;

    @Value("${logs.apache.initialOffset}")
    private Long logInitialOffset;


    @Autowired
    public ApacheLogCollector(ResourceLoader resourceLoader) {
        log.warn("Initializing log collector...");
        this.resourceLoader = resourceLoader;
    }

    @Scheduled(cron = "${logs.apache.scheduller.cronexp}")
    private void run(){
        try {
            readFileContent(logFolder, logEncoding, logInitialOffset);
        } catch (LogFileProcessException e) {
            log.error(String.format("Error reading log files form apache on folder %s", logFolder), e);
        }
    }

    @Override
    public List<String> readFileContent(String path, String encoding, Long offset) throws LogFileProcessException { //FIXME - should use stream
        log.warn(String.format("Reading content from file %s starting on line %s", path, offset));
        //Resource resource = resourceLoader.getResource(String.format("classpath:%s", path));
        List<String> lines = Collections.emptyList();

        try(InputStream is = Files.newInputStream(Paths.get(path), StandardOpenOption.READ)) {
            InputStreamReader isReader = new InputStreamReader(is, encoding);
            BufferedReader bReader = new BufferedReader(isReader);
            String line;
            while((line = bReader.readLine()) != null) {
                //TODO - to implement
                lines.add(line);
            }
        } catch (UnsupportedEncodingException  e) {
            throw new LogFileProcessException(e);
        } catch (IOException e) {
            throw new LogFileProcessException(e);
        }

        return Collections.unmodifiableList(lines);
    }
}
