package com.thinkstep.test.onlineusers.log.collector;

import com.thinkstep.test.onlineusers.log.collector.business.LogFileMetadataStorageService;
import com.thinkstep.test.onlineusers.log.collector.model.LogFileMetadata;
import com.thinkstep.test.onlineusers.log.processor.ApacheCombinedLogProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
    private ApacheCombinedLogProcessor apacheCombinedLogProcessor;


    @Autowired
    public ApacheLogCollector(ResourceLoader resourceLoader) {
        log.warn("Initializing log collector...");
        this.resourceLoader = resourceLoader;
    }

    @Scheduled(cron = "${logs.apache.scheduller.cronexp}")
    private void run() {
        try {
            List<String> files = scanForLogFiles(logFolder, "log");
            files.stream().forEach(fileName -> {
                try {
                    List<String> lines = readFileContent(logFolder, fileName, logEncoding, logInitialOffset);
                    if(lines != null && lines.size() > 0) {
                        apacheCombinedLogProcessor.submit(lines);
                    }
                } catch (LogFileProcessException e) {
                    log.error(String.format("Error reading log file %s", fileName), e);
                }
            });
        } catch (LogFileProcessException e) {
            log.error(String.format("Error reading log file list form apache on folder %s", logFolder), e);
        }
    }

    @Override
    public List<String> readFileContent(String folder, String fileName, String encoding, Long offset) throws LogFileProcessException { //FIXME - should use stream
        log.warn(String.format("Reading content from file %s/%s starting on line %s", folder, fileName, offset));
        //Resource resource = resourceLoader.getResource(String.format("classpath:%s", path));
        List<String> lines = new ArrayList<>();

        try (InputStream is = Files.newInputStream(Paths.get(String.format("%s/%s", folder, fileName)), StandardOpenOption.READ)) {

            InputStreamReader isReader = new InputStreamReader(is, encoding);
            BufferedReader bReader = new BufferedReader(isReader);
            String line;
            Long count = 0l;
            LogFileMetadata lineRef = apacheCombinedLogProcessor.getFileMetadata(folder, fileName);
            while ((line = bReader.readLine()) != null) {
                if(count > (lineRef.getLastProcessedLine() > 0 ? lineRef.getLastProcessedLine() : -1 )) {
                    lines.add(line);
                }
                count++;
            }
            lineRef.setLastProcessedLine(count);
            lineRef.setLastUpdate(Instant.now());
            apacheCombinedLogProcessor.save(lineRef);


        } catch (UnsupportedEncodingException e) {
            throw new LogFileProcessException(e);
        } catch (IOException e) {
            throw new LogFileProcessException(e);
        }

        return Collections.unmodifiableList(lines);
    }

    public List<String> scanForLogFiles(String folderPath, String ext) throws LogFileProcessException {
        try {
            final Path path = Paths.get(folderPath);
            //final PathMatcher filter = path.getFileSystem().getPathMatcher(String.format("^.*\\.(%s)$", ext));
            final Pattern extPattern = Pattern.compile(String.format("^.*\\.(%s)$", ext));

            final List<String> files =
                    Files.list(Paths.get(folderPath))
                            .sorted()
                            .filter(Files::isRegularFile)
                            .filter(Files::isReadable)
                            .map(file -> file.getFileName().toString()) //FIXME - add file extension filter
                            .collect(Collectors.toList());
            return files;

        } catch (IOException e) {
            throw new LogFileProcessException(e);
        }
    }


}
