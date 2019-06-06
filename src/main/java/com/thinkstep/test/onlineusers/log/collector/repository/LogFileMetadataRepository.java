package com.thinkstep.test.onlineusers.log.collector.repository;

import com.thinkstep.test.onlineusers.log.collector.model.LogFileMetadata;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface LogFileMetadataRepository extends CrudRepository<LogFileMetadata, Long> {

    @Query(value="from log_file_metadata l where l.folder = ?1 AND l.fileName = ?2 ORDER BY id desc")
    List<LogFileMetadata> findByFileName(String folder, String fileName);
}
