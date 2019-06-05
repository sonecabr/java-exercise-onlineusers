package com.thinkstep.test.onlineusers.log.collector.repository;

import com.thinkstep.test.onlineusers.log.collector.model.LogFileMetadata;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface LogFileMetadataRepository extends CrudRepository<LogFileMetadata, Long> {

    @Query(value="from log_file_metadata l where l.folder = ?1 AND l.fileName = ?1")
    LogFileMetadata findByFileName(String folder, String fileName);
}
